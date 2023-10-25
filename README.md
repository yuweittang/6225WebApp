# 6225WebApp
## Summary
This is a web application Library Management system built with spring boot and deployed on AWS

EC2 instances are built on a custom AMI using packer
Setting up the network and creation of resources is automated with Cloud formation, aws cli and shell scripts
Instances are autoscaled with ELB to handle the web traffic
Created a serverless application to facilitate the password reset functionality using SES and SNS
The application is deployed with Circle CI and AWS Code Deploy


## Tools and Technologies

### Infrastructure: VPC, ELB, EC2, Route53, RDS, Cloud formation, Terraform, Packer
### Webapp: Java, Spring Boot, MySQL, Maven
### CI/CD: Circle CI, AWS Code Deploy
