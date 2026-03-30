variable "project_name" {
  description = "Nombre base del proyecto para nombrar recursos"
  type        = string
}

variable "aws_region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1"
}

variable "db_username" {
  description = "Usuario de la base de datos"
  type        = string
  default     = "demo" # o el que hayas usado en RDS
}

variable "db_password" {
  description = "Password de la base de datos"
  type        = string
  sensitive   = true
}

variable "ecr_backend_url" {
  description = "URL completa del repositorio ECR para backend"
  type        = string
}

variable "ecr_frontend_url" {
  description = "URL completa del repositorio ECR para frontend"
  type        = string
}

variable "jwt_secret" {
  description = "JWT secret key"
  sensitive   = true  
}