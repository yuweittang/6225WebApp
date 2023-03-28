# resource "aws_security_group" "db_security_group" {
#   name_prefix = "db-security-group-"
#   description = "DB Security Group"

#   ingress {
#     from_port   = 3306
#     to_port     = 3306
#     protocol    = "tcp"
#     cidr_blocks = ["0.0.0.0/0"]
#   }
# }
# resource "aws_db_parameter_group" "mysql" {
#   name_prefix = "mysql-parameter-group"
#   family      = "mysql8.0"
#   description = "Custom parameter group for MySQL DB"

#   parameter {
#     name  = "character_set_server"
#     value = "utf8mb4"
#   }

#   parameter {
#     name  = "collation_server"
#     value = "utf8mb4_unicode_ci"
#   }
# }
# resource "aws_db_instance" "mysqlDB" {
#   name                 = "csye6225"
#   identifier           = "csye6225"
#   engine               = "mysql"
#   engine_version       = "5.7"
#   instance_class       = "db.t3.micro"
#   allocated_storage    = 10
#   username             = "csye6225"
#   password             = "examplepass666"
#   hostname             = ""
#   parameter_group_name = aws_db_parameter_group.mysql.id
#   multi_az             = false
#   publicly_accessible  = false
#   subnet_ids = [
#     "${aws_subnet.myprivatesubnet1.id}",
#     "${aws_subnet.myprivatesubnet2.id}",
#     "${aws_subnet.myprivatesubnet3.id}",
#   ]
#   vpc_security_group_ids = ["${aws_security_group.db_security_group.id}"]
# }
