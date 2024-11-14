import boto3
from botocore.exceptions import NoCredentialsError, PartialCredentialsError
from botocore.config import Config

def generate_presigned_url(bucket_name, file_name, access_key, secret_key):
    try:
        # S3 클라이언트 직접 생성
        s3_client = boto3.client(
            's3',
            aws_access_key_id=access_key,
            aws_secret_access_key=secret_key,
            region_name='ap-northeast-2'  # 서울 리전
        )

        # URL 생성을 위한 파라미터
        params = {
            'Bucket': bucket_name,
            'Key': file_name,
            'ContentType': 'text/plain'  # Content-Type 명시적 지정
        }

        # Presigned URL 생성
        try:
            url = s3_client.generate_presigned_url(
                ClientMethod='put_object',
                Params=params,
                ExpiresIn=3600,  # 1시간
                HttpMethod='PUT'
            )
            
            print(url)
    
            return url

        except Exception as e:
            print(f"Error generating URL: {str(e)}")
            return None

    except NoCredentialsError:
        print("Error: AWS credentials not found")
        return None
    except PartialCredentialsError:
        print("Error: Incomplete AWS credentials")
        return None
    except Exception as e:
        print(f"Unexpected error: {str(e)}")
        return None

# Usage
bucket_name = 'acc-hongik-bucket'
file_name = 'acc-hongik.txt'
access_key = ''
secret_key = ''

generate_presigned_url(bucket_name, file_name, access_key, secret_key)