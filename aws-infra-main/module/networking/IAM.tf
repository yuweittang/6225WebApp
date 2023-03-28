# resource "aws_iam_role" "my_role" {
#   name = "EC2-CSYE6225"

#   assume_role_policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [
#       {
#         "Version" : "2012-10-17",
#         "Statement" : [
#           {
#             "Action" : [
#               "s3:PutObject",
#               "s3:PutObjectAcl",
#               "s3:GetObject",
#               "s3:GetObjectAcl",
#               "s3:DeleteObject"

#             ],
#             "Effect" : "Allow",
#             "Resource" : [
#               "arn:aws:s3:::YOUR_BUCKET_NAME",
#               "arn:aws:s3:::YOUR_BUCKET_NAME/*"
#             ]
#           }
#         ]
#       }
#     ]
#   })

# }

# resource "aws_iam_instance_profile" "my_instance_profile" {
#   name = "my-instance-profile"

#   role = aws_iam_role.my_role.name
# }
