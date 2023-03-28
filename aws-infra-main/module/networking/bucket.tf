# provider "aws" {

#   access_key = var.aws_access_key

#   secret_key = var.aws_secret_key

#   region = var.region

# }

# resource "random_pet" "bucket_name" {}
# resource "aws_s3_bucket" "private_bucket" {
#   bucket = "${lower(random_pet.bucket_name.id)}-${terraform.workspace}-s3-bucket"
#   acl    = "private"

#   server_side_encryption_configuration {
#     rule {
#       apply_server_side_encryption_by_default {
#         sse_algorithm = "AES256"
#       }
#     }
#   }

#   lifecycle_rule {
#     id     = "transition-to-ia"
#     status = "Enabled"

#     transition {
#       days          = 30
#       storage_class = "STANDARD_IA"
#     }
#   }

#   force_destroy = true
# }
