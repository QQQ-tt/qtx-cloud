#!/bin/bash
# shellcheck disable=SC2164
cd /cloud
mvn clean package
start1() {
  echo "starting gateway....."
  nohup java -jar -Xmx128m /cloud/service-gateway/target/service-gateway-1.0.jar >gateway.log &
  sleep 10s
}
start2() {
  echo "starting auth....."
  nohup java -jar -Xmx256m /cloud/service/service-auth/target/service-auth-1.0.jar >auth.log &
  sleep 10s
}
start3() {
  echo "starting oss....."
  nohup java -jar -Xmx128m /cloud/service/service-oss/target/service-oss-1.0.jar >oss.log &
  sleep 10s
}
start4() {
  echo "starting activity....."
  nohup java -jar -Xmx256m /cloud/service/service-activity/target/service-activity-1.0.jar >activity.log &
  sleep 10s
}
#定义一个方法pid_health_check $1是这个方法的参数,其他地方调用此方法传入进来
pid_health_check() {
  #定义一个linux的命令字符串 $1是这个方法的参数类似于(ps -ef|grep tomcat 的命令),其他地方调用此方法传入进来
  #这个命令解释: 搜索linux中的进程 并且过滤掉-带grep的那条
  # shellcheck disable=SC2009
  # shellcheck disable=SC2126
  process_cnt=$(ps -ef | grep "$1" | grep -v grep | wc -l)
  #表示执行上面定义的process_cnt命令
  echo "$process_cnt"
  #返回值-上面命令查询出来的日志的条数
  return $?
}
##在脚本最后一行添加tail -f /dev/null，这个命令永远完成不了，所以该脚本一直不会执行完，所以该容器永远不会退出
##tail -f /dev/null
##我这里写的死循环就不用上面的那个查看日志的命令了
start1
start2
start3
start4

while [[ 1 -gt 0 ]]; do
  echo "check healthy of gateway...."
  process_cnt=$(pid_health_check gateway)
  ##if语句 (ps -ef|grep relations-analysis)查询结果小于1表示此服务已经停了
  if [[ $process_cnt -lt 1 ]]; then
    echo "start1"
    start1
  fi
  echo "check healthy of auth...."
  process_cnt=$(pid_health_check auth)
  if [[ $process_cnt -lt 1 ]]; then
    echo "start2"
    start2
  fi
  echo "check healthy of oss...."
  process_cnt=$(pid_health_check oss)
  ##if语句 (ps -ef|grep relations-analysis)查询结果小于1表示此服务已经停了
  if [[ $process_cnt -lt 1 ]]; then
    echo "start3"
    start3
  fi
  ##睡眠5分钟
  sleep 5m
done