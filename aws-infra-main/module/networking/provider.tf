provider "aws" {
  region  = "us-west-2"
  profile = "demo"
}

variable "key_name" {}

resource "tls_private_key" "example" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "generated_key" {
  key_name   = var.key_name
  public_key = tls_private_key.example.public_key_openssh
}


resource "aws_security_group" "sg" {
  name = "aws_security_group.sg"

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
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "security_group_id" {
  value = aws_security_group.sg.id
}

# data "template_file" "mysql_install_script" {
#   template = file("mysql_install_script.sh")
# }


resource "aws_instance" "web" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  vpc_security_group_ids = [aws_security_group.sg.id]
  key_name               = aws_key_pair.generated_key.key_name
  # iam_instance_profile   = aws_iam_instance_profile.my_instance_profile.name
  #   userdata               = <<EOF

  # #!/bin/bash

  # ####################################################

  # # Configure Tomcat JAVA_OPTS #

  # ####################################################


  # cd /opt/tomcat/bin

  # touch setenv.sh

  # echo "#!/bin/sh" > setenv.sh


  # echo "export DB_USERNAME=${var.database_username}" >> /etc/profile.d/myapp.sh
  # echo "export DB_PASSWORD=${var.database_password}" >> /etc/profile.d/myapp.sh
  # echo "export DB_HOSTNAME=${aws_db_instance.mysqlDB.endpoint}" >> /etc/profile.d/myapp.sh
  # echo "export S3_BUCKET_NAME=${aws_s3_bucket.private_bucket.id}" >> /etc/profile.d/myapp.sh
  # chown tomcat:tomcat setenv.sh

  # chmod +x setenv.sh

  # sudo chmod 755 -R /opt/tomcat

  # # Start Tomcat

  # /bin/bash /opt/tomcat/bin/catalina.sh start
  # #install mysql client 



  #  EOF
  #   root_block_device {
  #     volume_size = var.root_volume_size
  #     volume_type = var.root_volume_type
  #   }

  #   lifecycle {
  #     prevent_destroy = false
  #   }
  #   user_data = data.template_file.mysql_install_script.rendered

  #   tags = {
  #     Name = "my-instance"
  #   }
  #   provisioner "remote-exec" {
  #     connection {
  #       type        = "ssh"
  #       user        = "ec2-user"
  #       private_key = file("~/.ssh/ec2")
  #       host        = aws_instance.web.public_ip
  #     }

  #     inline = [
  #       "echo hello world"
  #     ]
  #   }
}

variable "ami_id" {
  type    = string
  default = "ami-0846eec981e2d481f"
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




