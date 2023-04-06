resource "aws_subnet" "myprivatesubnet1" {
  vpc_id            = aws_vpc.yvot.id
  cidr_block        = "10.20.64.0/20"
  availability_zone = "us-west-2a"
}

resource "aws_subnet" "myprivatesubnet2" {
  vpc_id            = aws_vpc.yvot.id
  cidr_block        = "10.20.96.0/20"
  availability_zone = "us-west-2b"
}

resource "aws_subnet" "myprivatesubnet3" {
  vpc_id            = aws_vpc.yvot.id
  cidr_block        = "10.20.32.0/20"
  availability_zone = "us-west-2c"
}
resource "aws_subnet" "mypublicsubnet1" {
  vpc_id            = aws_vpc.yvot.id
  cidr_block        = "10.20.48.0/20"
  availability_zone = "us-west-2a"
}
resource "aws_subnet" "mypublicsubnet2" {
  vpc_id            = aws_vpc.yvot.id
  cidr_block        = "10.20.0.0/20"
  availability_zone = "us-west-2b"
}
resource "aws_subnet" "mypublicsubnet3" {
  vpc_id = aws_vpc.yvot.id

  cidr_block        = "10.20.80.0/20"
  availability_zone = "us-west-2c"
}
