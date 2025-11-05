## Model: Employee
| Attribute | Type | Defined By | Constraints |
|---|---:|---|---|
| id | integer | Developer | > 0 |
| fName | string | User | 1-50, includes: a-z, ', -, ., " " |
| lName | string | User | 1-50, includes: a-z, ', -, ., " " |
| country | string | User | 1-50, includes: a-z, " " |
| dept | string | User | 1-50, includes: a-z |
| title | string | User | 1-50, includes: a-z |

## Model: Room
| Attribute | Type | Defined By | Constraints |
|---|---:|---|---|
| roomId | integer | Developer | > 0 |
| city | string | User | 1-50, includes: letters |
| address | string | User | 1-50, includes: a-z, ', -, 0-9 |
| roomNumber | string | User | 1-20, a-z, -, 0-9 |
| type | string | User | 1-20, a-z |
| seats | integer | User | 1-250 |

## Model: Reservation
| Attribute | Type | Defined By | Constraints |
|---|---:|---|---|
| ID | integer | Developer | > 0 |
| startTime | datetime | User | >= current time |
| endTime | datetime | User | > startTime, endTime + startTime <= 12h |
| owner | string | User | 1-100, includes: all in first and last name |
| ownerID | integer | Developer | > 0 |
| rooms | list of `Room` | User | length > 0, only > 1 if video rooms |

## Model: TokenRequest
| Attribute | Type | Defined By | Constraints |
|---|---:|---|---|
| token | string | Auth Server | 250-400 |