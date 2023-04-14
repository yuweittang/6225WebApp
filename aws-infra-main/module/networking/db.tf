resource "aws_security_group" "db_security_group" {
  name_prefix = "db-security-group"
  description = "DB Security Group"
  vpc_id      = aws_vpc.yvot.id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_subnet_group" "mysql" {
  name        = "sqldb-subnet-group"
  description = "sqldb subnet group"


  subnet_ids = [
    "${aws_subnet.myprivatesubnet1.id}",
    "${aws_subnet.myprivatesubnet2.id}",
    "${aws_subnet.myprivatesubnet3.id}",
  ]
}
resource "aws_db_parameter_group" "mysql" {
  name_prefix = "mysql-parameter-group"
  family      = "mysql5.7"
  description = "Custom parameter group for MySQL DB"


  parameter {
    name  = "character_set_server"
    value = "utf8mb4"
  }

  parameter {
    name  = "collation_server"
    value = "utf8mb4_unicode_ci"
  }

  parameter {
    name  = "max_connections"
    value = "1000"
  }
  parameter {
    name  = "innodb_buffer_pool_size"
    value = "2147483648"
  }
}
resource "aws_db_instance" "mysqlDB" {
  db_name           = "csye6225"
  identifier        = "csye6225"
  engine            = "mysql"
  engine_version    = "5.7"
  instance_class    = "db.t3.micro"
  allocated_storage = 10
  username          = "csye6225"
  password          = "examplepass666"

  multi_az               = false
  publicly_accessible    = false
  parameter_group_name   = aws_db_parameter_group.mysql.name
  db_subnet_group_name   = aws_db_subnet_group.mysql.name
  vpc_security_group_ids = ["${aws_security_group.db_security_group.id}"]
}

resource "null_resource" "mysqlDB" {
  provisioner "local-exec" {
    command = "mysql -h ${aws_db_instance.mysqlDB.endpoint} -u ${aws_db_instance.mysqlDB.username} -p${aws_db_instance.mysqlDB.password} -e \"CREATE DATABASE csye6225\""
  }

  depends_on = [aws_db_instance.mysqlDB]
}

