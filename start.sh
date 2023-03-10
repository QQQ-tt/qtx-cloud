#!/bin/bash
start1(){
    echo "starting gateway....."
    nohup java -jar /gateway.jar > gateway.log &
}
start2(){
    echo "starting auth....."
    nohup java -jar /auth.jar > auth.log &
}
#定义一个方法pid_health_check $1是这个方法的参数,其他地方调用此方法传入进来
pid_health_check(){
    #定义一个linux的命令字符串 $1是这个方法的参数类似于(ps -ef|grep tomcat 的命令),其他地方调用此方法传入进来
    #这个命令解释: 搜索linux中的进程 并且过滤掉-带grep的那条 
    process_cnt=`ps -ef | grep $1 | grep -v grep | wc -l`
    #表示执行上面定义的process_cnt命令
    echo $process_cnt
    #返回值-上面命令查询出来的日志的条数
    return $?
}
##在脚本最后一行添加tail -f /dev/null，这个命令永远完成不了，所以该脚本一直不会执行完，所以该容器永远不会退出
##tail -f /dev/null
##我这里写的死循环就不用上面的那个查看日志的命令了
start1
start2
 
while [[ 1 -gt 0 ]]
do
    echo "check healthy of gateway...."
    process_cnt=$(pid_health_check gateway)
    ##if语句 (ps -ef|grep relations-analysis)查询结果小于1表示此服务已经停了
    if [[ $process_cnt -lt 1 ]]
    then
       echo "start1"
        start1
    fi
    echo "check healthy of admin-client...."
    process_cnt=$(pid_health_check admin)
    if [[ $process_cnt -lt 1 ]]
    then
        echo "start2"
        start2
    fi
    ##睡眠1
    sleep 1m
done