# RawBlob
Simple application to manage azure storage container.
Similar to my [old project](https://github.com/RobertMut/MyIssue) it uses raw TCP sockets to communicate rather than prepared libraries and protocols.
This project consist of two parts:
 - RawBlob - just server which is connected to azure and uses key vault to get connection strings to storage
 - RawBlobClient - javafx client used to connect to server, it doesn't know about azure and other stuff
 
##Screenshots
![RawBlobClient](https://i.imgur.com/RjgIKTu.png) 
![RawBlob](https://i.imgur.com/AwL84g2.png) 

##ToDo
 - Unit tests
 - Maybe MVC for RawBlobClient
