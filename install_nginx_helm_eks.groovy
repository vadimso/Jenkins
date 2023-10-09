pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID     = ''
        AWS_SECRET_ACCESS_KEY = ''
        HELM_INSTALL_DIR = "${WORKSPACE}/helm"
        HELM_EXPERIMENTAL_OCI='1'
        AWSCLI_INSTALL_DIR = "${WORKSPACE}/awscli"
    }

    stages { 
        // stage('Install AWS CLI') {
        //     steps {
        //         //def osVersion = sh(script: 'cat /etc/os-release | grep "PRETTY_NAME"', returnStatus: true).trim()
        //         //sh "echo "Linux OS Version: ${osVersion}"
        //         //sh 'apt-get update && apt-get install -y curl'
        //         sh 'yum install -y curl'
        //         sh "${AWSCLI_INSTALL_DIR}/awscli/curl https://d1vvhvl2y92vvt.cloudfront.net/awscli-exe-linux-x86_64.zip -o awscliv2.zip"
        //         //sh 'curl "https://d1vvhvl2y92vvt.cloudfront.net/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
        //         sh 'unzip awscliv2.zip'
        //         sh './aws/install'
        //     }
        // }
        
        stage('Install Helm') {
            steps {
                //script {
                   // def helmBinaryPath = "/usr/local/bin/helm"
                //}
                //sh 'curl https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 | bash'
                    sh """
                    mkdir -p ${HELM_INSTALL_DIR}
                    cd ${HELM_INSTALL_DIR}
                    curl -LO https://get.helm.sh/helm-v3.7.0-linux-amd64.tar.gz
                    tar -zxvf helm-v3.7.0-linux-amd64.tar.gz
                    """
            }
        }

        // stage('Configure AWS CLI') {
        //     steps {
        //         sh 'aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID'
        //         sh 'aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY'
        //         sh 'aws configure set default.region <Your_AWS_Region>'
        //     }
        // }

        stage('Deploy Nginx Helm Chart') {
            steps {
                
                sh "ls ${HELM_INSTALL_DIR}/linux-amd64/"
                //sh "${HELM_INSTALL_DIR}/linux-amd64/helm uninstall my-nginx-release/nginx"
                sh "${HELM_INSTALL_DIR}/linux-amd64/helm repo add bitnami https://charts.bitnami.com/bitnami"
                sh "${HELM_INSTALL_DIR}/linux-amd64/helm install nginx bitnami/nginx --set image.tag=latest"
                //sh "${HELM_INSTALL_DIR}/linux-amd64/helm install my-release oci://registry-1.docker.io/bitnamicharts/nginx"
                //sh 'helm repo add bitnami https://charts.bitnami.com/bitnami'
                //sh 'helm install my-nginx-release bitnami/nginx'
            }
        }
    }
}
