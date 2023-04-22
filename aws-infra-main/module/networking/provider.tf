provider "aws" {
  region     = "us-west-2"
  profile    = "demo"
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}


variable "region" {

  default = "us-west-2"

}

output "region" {
  value = var.region
}
resource "aws_security_group" "web" {
  name   = "aws_security_group.web"
  vpc_id = aws_vpc.yvot.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# resource "aws_key_pair" "ec2_public" {
#   key_name   = "public-keypair"
#   public_key = file("~/.ssh/id_rsa.pub")
# }

# resource "aws_key_pair" "ec2_private" {
#   key_name   = "private-keypair"
#   public_key = file("~/.ssh/id_rsa")
# }

# output "security_group_id" {
#   value = aws_security_group.web.id
# }

# data "template_file" "mysql_install_script" {
#   template = file("mysql_install_script.sh")
#var.aws_access_key
#var.aws_secret_key
# }

resource "aws_cloudwatch_log_group" "web" {
  name = "/csye6225/logs"
}

resource "aws_cloudwatch_log_stream" "web" {
  name           = "csye6225"
  log_group_name = aws_cloudwatch_log_group.web.name
}

resource "aws_instance" "web" {
  ami                         = var.get_ami
  key_name                    = "ec2"
  instance_type               = var.instance_type
  vpc_security_group_ids      = [aws_security_group.web.id]
  iam_instance_profile        = aws_iam_instance_profile.ec2.name
  subnet_id                   = aws_subnet.mypublicsubnet3.id
  associate_public_ip_address = true
  # connection {
  #   type        = "ssh"
  #   user        = "ec2-user"
  #   private_key = file("~/.ssh/ec2")
  #   timeout     = "2m"
  # }

  user_data = <<-EOF

#!/bin/bash
cd /usr/local/tomcat/bin

touch setenv.sh


echo "#!/bin/sh" > setenv.sh

echo "export DB_USERNAME=${var.database_username}" >> setenv.sh
echo "export DB_PASSWORD=${var.database_password}" >> setenv.sh
echo "export DB_HOSTNAME=${aws_db_instance.mysqlDB.endpoint}" >> setenv.sh

sudo chown ec2-user:ec2-user setenv.sh
chmod +x setenv.sh


sudo chmod 755 -R /usr/local/tomcat

# Start Tomcat
sudo /usr/local/tomcat/bin/startup.sh

yum install -y mysql

echo "export S3_BUCKET_NAME=${aws_s3_bucket.private_bucket.id}" >> /usr/local/tomcat/bin/setenv.sh

source setenv.sh

# Install CloudWatch Agent
yum install -y amazon-cloudwatch-agent

# Configure CloudWatch Agent
echo "Creating CloudWatch Agent config file..."
cat > /opt/aws/amazon-cloudwatch-agent/bin/config.json <<-CONFIG
{
    "agent": {
        "metrics_collection_interval": 10,
        "logfile": "/var/logs/amazon-cloudwatch-agent.log"
    },
    "logs": {
        "logs_collected": {
            "files": {
                "collect_list": [
                    {
                        "file_path": "/usr/local/tomcat/logs/csye6225.log",
                        "log_group_name": "${aws_cloudwatch_log_group.web.name}",
                        "log_stream_name": "${aws_cloudwatch_log_stream.web.name}"
                    }
                ]
            }
        }
    },
  "metrics":{
    "metrics_collected":{
       "statsd":{
          "service_address":":8125",
          "metrics_collection_interval":15,
          "metrics_aggregation_interval":300
       }
    }
 }
}
CONFIG

# Start CloudWatch Agent
/opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c file:/opt/aws/amazon-cloudwatch-agent/bin/config.json -s

EOF

}

resource "aws_route53_record" "webapp" {
  zone_id = "Z06282863G0ABITAP79RM"
  name    = "prod.yvot.me.tld"
  type    = "A"
  ttl     = "300"
  records = ["${aws_instance.web.private_ip}"]
}


output "public_ip" {
  value = aws_instance.web.public_ip
}
# variable "public_key" {
#   type = string
# }
# variable "private_key" {
#   type = string
# }

variable "get_ami" {
  type = string
}

variable "aws_access_key" {

}
variable "aws_secret_key" {

}
variable "instance_type" {
  type    = string
  default = "t2.micro"
}


variable "security_group_name" {
  type        = string
  description = "The name of the security group that should be applied to the instance."
}

variable "root_volume_size" {
  type    = number
  default = "50"
}

variable "root_volume_type" {
  type    = string
  default = "gp2"
}

variable "database_username" {
  type    = string
  default = "csye6225"
}

variable "database_password" {
  type    = string
  default = "examplepass666"
}

#create s3 bucket
resource "random_id" "bucket_id" {
  byte_length = 8
}

resource "aws_s3_bucket" "private_bucket" {
  bucket        = "my-bucket-${random_id.bucket_id.hex}"
  acl           = "private"
  force_destroy = true

  versioning {
    enabled = true
  }

  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }

  lifecycle_rule {

    id      = "transition-to-IA-storage"
    enabled = true

    transition {
      days          = 30
      storage_class = "STANDARD_IA"

    }
  }
}

output "bucket_name" {
  value = aws_s3_bucket.private_bucket.id
}

#IAM 

resource "aws_iam_policy" "webapp_s3_policy" {
  name = "webapp-s3-policy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "s3:PutObject",
          "s3:PutObjectAcl",
          "s3:GetObject",
          "s3:GetObjectAcl",
          "s3:DeleteObject"
        ]
        Effect = "Allow"
        Resource = [
          "arn:aws:s3:::YOUR_BUCKET_NAME",
          "arn:aws:s3:::YOUR_BUCKET_NAME/*"
        ]
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "webapp_s3_attachment" {
  policy_arn = aws_iam_policy.webapp_s3_policy.arn
  role       = aws_iam_role.webapp.name
}



resource "aws_iam_role" "webapp" {
  name = "my-ec2-instance"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }

      }
    ]
  })
}


resource "aws_iam_policy" "cloudwatch_logs_policy" {
  name        = "cloudwatch_logs_policy"
  description = "Allows EC2 instances to write to CloudWatch Logs"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "cloudwatch_logs_policy_attachment" {
  policy_arn = aws_iam_policy.cloudwatch_logs_policy.arn
  role       = aws_iam_role.webapp.name
}

resource "aws_iam_instance_profile" "ec2" {
  name = "iam_ec2"
  role = aws_iam_role.webapp.name
}

resource "aws_security_group" "load_balancer_sg" {
  name_prefix = "load_balancer_sg_"
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {
    Name = "load_balancer_sg"
  }
}


