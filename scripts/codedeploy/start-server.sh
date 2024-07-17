#!/bin/bash

# S3로 부터 빌드파일 다운 받은 후~
echo "--------------- 서버 배포 시작 -----------------"
cd /home/ubuntu/concert-booking-server
sudo fuser -k -n tcp 8080 || true
nohup java -jar concert-booking.jar > ./output.log 2>&1 &
echo "--------------- 서버 배포 끝 -----------------"