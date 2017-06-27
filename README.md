# GJGS
Generic Java Game Server

Server Structure: 

Server Class

- accepts Messages from Client 
- converts incoming messages into a ServerEvent
- ServerEvents get passed to a Worker

GSGJWorker Class:

- gets ServerEvents from Server
- manages all BaseServices and Services
- hands ServerEvents to all BaseServices and Services


Service Class:

- is a Service which is provided by a user plugin
- in the end it will be the main class for the game itself
- gives the server additionally features 


BaseService Class:

- extends Service
- a BaseService is a core element for the server
- could be something like User-, Lobby-, GameManagement


Notes:
Netty communication is provides by biasedbit:
https://github.com/biasedbit/netty-tutorials

