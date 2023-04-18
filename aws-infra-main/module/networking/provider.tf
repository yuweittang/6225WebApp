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
    from_port   = 8081
    to_port     = 8081
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

resource "aws_key_pair" "ec2_public" {
  key_name   = "public-keypair"
  public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_key_pair" "ec2_private" {
  key_name   = "private-keypair"
  public_key = file("~/.ssh/id_rsa")
}

# output "security_group_id" {
#   value = aws_security_group.web.id
# }

# data "template_file" "mysql_install_script" {
#   template = file("mysql_install_script.sh")
#var.aws_access_key
#var.aws_secret_key
# }


resource "aws_instance" "web" {
  ami                         = var.get_ami
  instance_type               = var.instance_type
  vpc_security_group_ids      = [aws_security_group.web.id]
  iam_instance_profile        = aws_iam_instance_profile.ec2.name
  subnet_id                   = aws_subnet.mypublicsubnet3.id
  associate_public_ip_address = true
  connection {
    type        = "ssh"
    user        = "ec2-user"
    private_key = aws_key_pair.ec2_private.key_name
    timeout     = "2m"
  }

  user_data = <<-EOF

  #!/bin/bash


  cd /opt/tomcat/bin

  touch setenv.sh

  echo "#!/bin/sh" > setenv.sh

  echo "export DB_USERNAME=${var.database_username}" >> setenv.sh
  echo "export DB_PASSWORD=${var.database_password}" >> setenv.sh
  echo "export DB_HOSTNAME=${aws_db_instance.mysqlDB.endpoint}" >> setenv.sh


  chown tomcat:tomcat setenv.sh

  chmod +x setenv.sh
  source /opt/tomcat/bin/setenv.sh

  sudo chmod 755 -R /opt/tomcat

  # Start Tomcat
  
  /bin/bash /opt/tomcat/bin/catalina.sh start
  yum install mysql client 

  echo "export S3_BUCKET_NAME=${aws_s3_bucket.private_bucket.id}" >> /opt/tomcat/bin/setenv.sh
  
  root_block_device {
    volume_size = var.root_volFume_size
    volume_type = var.root_volume_type
  }

  lifecycle {
    prevent_destroy = false
  }

  tags = {
    Name = "my-instance"
  }
  }
    EOF
}


output "public_ip" {
  value = aws_instance.web.public_ip
}
variable "public_key" {
  type = string
}
variable "private_key" {
  type = string
}

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


resource "aws_iam_instance_profile" "ec2" {
  name = "iam_ec2"
  role = aws_iam_role.webapp.name
}

