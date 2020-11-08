This is SpringBoot project with Gradle.

About Code:

Created 3 worker Thread.

Worker 1:
---------------------------------
As of now worker 1 work is to read file of given input path. I have created
IReader interface, So in future if any more reading mechanism requires we can use that directly
with creating new class and use in our main app.

Worker 1 read data and put that data in BlockingQueue<Trade>.

Thats the job of worker 1.

Worker 2:
---------------------------------------
Worker 2 will use BlockingQueue<Trade> queue to get trade. Based on its timestamp it will generate
OHLC pojo.

I have created one service called SharedDataService. Which will be responsible for provide
when required.

SharedData as map in Map<String, OHCLWithStartAndEndTime>. Here key is SYMBOL
and  OHCLWithStartAndEndTime is OHCL object with start and end time. Start and end time is used to
maintain interval and according send it to subscriber.

If there is no OHLC in SharedData it will create new data with start and end timeand put that into map.
if already present calculate OHLC.

If trade is having time which is more then end time of current OHLC. That means interval got ended.
Send OHLC to SharedData maintainer and create new OHLC for next interval. Logic is also written to
create blank response as well as time is very big and not fit into next interval.

Shared Data have Map<String, Deque<OHLC>>.
So for whatever symbol we are generating OHCL we will maintain
queue that will allow us to send OHLC in order.

Worker 2 will put the calculated data in Map<String, Deque<OHLC>>.

Worker 3:
--------------------------------------

Worker 3 is using publisher subscriber model. We have Subject as Symbol sybject and its list of Observers.

Shared data have : Map<String, SubjectImpl> key as symbol and value as Subject.
This map will have data of subscriber that we can use in WebSocket.

In Code i am iterating over this map and get latest OHLC from Map<String, Deque<OHLC>> and send it to
clients.

I have created worker 3 as scheduler thread which will run every 15 second.

-------------------------------------------------------------------------------------------------

How to run:
------------------
Create jar of this project.
You will need to build the jar file first. Here is the syntax to run the main class from a jar file.

Commands:
gradle build
java -jar target/upstoxassignment0.0.1-SNAPSHOT.jar "classpath:sample.json" "Bloomberg" "XXBTZUSD"

or

./gradlew bootRun "classpath:sample.json" "Bloomberg" "XXBTZUSD"

Paramter 1: FileName that we want to load
Paramter 2: Observer Name
Paramter 3: Observer want to observe which Symbol.


Test Case:
--------------------------------------

Running tese with SpringBootTest.
Passing expected file data and observer name and observer SYMBOL as input.

Worker 1 will read file.
Worker 2 will create OHLC.
Worker 3 will not run automatically because i have used spring configuration. So we use it as bean here.
get data from Worker 3 and asserting it with expected objects.
