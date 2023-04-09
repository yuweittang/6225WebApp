
module "mynetwork" {
  source              = "./module/networking"
  cidr                = "10.20.0.0/16"
  security_group_name = "aws_security_group.sg"
  get_ami             = data.aws_ami.ami.id
  aws_access_key      = var.aws_access_key
  aws_secret_key      = var.aws_secret_key
  public_key          = var.public_key
  private_key         = var.private_key

}
provider "aws" {
  region     = "us-west-2"
  profile    = "demo"
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}
data "aws_ami" "ami" {
  most_recent = true
  owners      = ["056163450361"]
  filter {
    name   = "name"
    values = ["csye6225"]
  }
}

variable "aws_access_key" {

}

variable "aws_secret_key" {

}
variable "public_key" {


}

variable "private_key" {


}





