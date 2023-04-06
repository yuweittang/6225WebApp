
module "mynetwork" {
  source              = "./module/networking"
  cidr                = "10.20.0.0/16"
  security_group_name = "aws_security_group.sg"
  # key_name = ""
}








