
## Packages
| Package | Dependencies|
| - | - |
| Model | None |
| Controller | SpringBoot, Tomcat |
| Service | None |
| Repository | SQL (Standard Library) |
| Util | Log4J2 |
| Config | None |
| Tests | JUnit |

## Data Classes
| Class | Package | Description |
| - | - | - |
| Room | Model | Room info incl. Location, Seating, etc. |
| Reservation | Model | Reservation info incl. Schedule, Employee, Rooms, etc. | 
| Employee | Model | Represents ID, name, role etc.|


## Routes
| URI | Protocol | Description |
| - | - | - |
| /rooms | GET | Returns a list of rooms |
| /rooms/{ID} | GET | Get room details by ID |
| /rooms | POST | Create a room from JSON, returns ID |
| /rooms/{ID} | PUT | Update room by ID |
| /rooms/{ID} | DELETE | Delete room by ID |
| /reservations | GET | Returns a list of reservations |
| /reservations/{ID} | GET | Get reservation details by ID |
| /reservations | POST | Create reservation from JSON, returns ID | 
| /reservations/{ID} | PUT | Update reservation by ID |
| /reservations/{ID} | DELETE | Delete reservation by ID | 

## Config
TODO