import boto3
from botocore.exceptions import NoCredentialsError, PartialCredentialsError
from datetime import datetime, timedelta

def generate_presigned_url(bucket_name, file_name, access_key, secret_key):
    try:
        # S3 클라이언트 생성
        s3_client = boto3.client(
            's3',
            aws_access_key_id=access_key,
            aws_secret_access_key=secret_key,
            region_name='ap-northeast-2'  # 서울 리전
        )

        # Presigned URL 발급을 위한 요청 객체 생성
        expiration = datetime.now() + timedelta(minutes=10)  # URL 유효기간 설정 (10분)
        presigned_url = s3_client.generate_presigned_url(
            'put_object',
            Params={
                'Bucket': bucket_name,
                'Key': file_name,
                'ACL': 'private'  # Private 권한 적용
            },
            ExpiresIn=int(expiration.timestamp() - datetime.now().timestamp())
        )

        print(presigned_url)

    except NoCredentialsError:
        print("Credentials not available.")
    except PartialCredentialsError:
        print("Incomplete credentials provided.")
    except Exception as e:
        print(f"An error occurred: {e}")

# Usage
bucket_name = 'acc-hongik-bucket'
file_name = 'acc-hongik.txt'
access_key = ''
secret_key = ''

generate_presigned_url(bucket_name, file_name, access_key, secret_key)