 resource "aws_route_table" "publRT" {    
 vpc_id = aws_vpc.yvot.id
   route {
   cidr_block = "0.0.0.0/0"            
   gateway_id=aws_internet_gateway.gw.id
   }
 }

 resource "aws_route_table" "prilRT" {
 vpc_id = aws_vpc.yvot.id
}

resource "aws_route_table_association" "public1"{
subnet_id=aws_subnet.mypublicsubnet1.id
route_table_id=aws_route_table.publRT.id

}
 

resource "aws_route_table_association" "public2"{
subnet_id=aws_subnet.mypublicsubnet2.id
route_table_id=aws_route_table.publRT.id
}

resource "aws_route_table_association" "public3"{
subnet_id=aws_subnet.mypublicsubnet3.id
route_table_id=aws_route_table.publRT.id
}


resource "aws_route_table_association" "private1"{
subnet_id=aws_subnet.myprivatesubnet1.id
route_table_id=aws_route_table.prilRT.id
}
resource "aws_route_table_association" "private2"{
subnet_id=aws_subnet.myprivatesubnet2.id
route_table_id=aws_route_table.prilRT.id
}
resource "aws_route_table_association" "private3"{
subnet_id=aws_subnet.myprivatesubnet3.id
route_table_id=aws_route_table.prilRT.id
}
