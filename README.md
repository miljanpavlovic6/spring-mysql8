Test Project - Game Achievements API

After opening project:  
    step 1: check application.yml
    step 2: check database.sql - create database, tables and insert all...with Mysql Workbench
    step 3: run project with intelliJ/eclipse

API Specification:
//Create Achievement
POST     http://localhost:8082/achievement/add  

body>formdata:

displayName: MasterUltraGiga
description: Description
displayOrder: 12
gameId: 111

result:
{
    "achievementId": ";dr}=>u4Q\\qrVB;{f",
    "displayName": "MasterUltraGiga",
    "description": "Descript",
    "icon": "https:/robohash.org/MasterUltraGiga",
    "displayOrder": 12,
    "created": "2021-11-06T15:37:10.472+0000",
    "updated": "2021-11-06T15:37:10.472+0000"
}

  //Get all game Achievements for suplied gameId
GET     http://localhost:8082/achievement/list/111

result:
[
    {
        "achievementId": "21",
        "displayName": "Beginner",
        "description": "The definition of a beginner.",
        "icon": "www.gom.com",
        "displayOrder": 1,
        "created": "2008-07-03T22:00:00.000+0000",
        "updated": "2008-07-03T22:00:00.000+0000"
    },
    {
        "achievementId": "125yhy",
        "displayName": "Expert",
        "description": "The definition of a expert.",
        "icon": "www.gom.com",
        "displayOrder": 3,
        "created": "2008-07-03T22:00:00.000+0000",
        "updated": "2008-07-03T22:00:00.000+0000"
    },
    {
        "achievementId": "r~93aN|aq>D4RJ?MR",
        "displayName": "Miljan bog",
        "description": "Miljan je bogfdsafsdfdsf",
        "icon": "https:/robohash.org/Miljan%20bog",
        "displayOrder": 10,
        "created": "2021-11-06T14:52:42.330+0000",
        "updated": "2021-11-06T14:52:42.330+0000"
    },
    {
        "achievementId": "%F~Y$v/L'!-MG9v4O",
        "displayName": "Jelnea",
        "description": "Miljan je bogfdsafsdfdsf",
        "icon": "https:/robohash.org/Jelnea",
        "displayOrder": 11,
        "created": "2021-11-06T14:54:21.000+0000",
        "updated": "2021-11-06T14:54:21.000+0000"
    },
    {
        "achievementId": ";dr}=>u4Q\\qrVB;{f",
        "displayName": "MasterUltraGiga",
        "description": "Descript",
        "icon": "https:/robohash.org/MasterUltraGigaaaa.png",
        "displayOrder": 12,
        "created": "2021-11-06T15:37:10.472+0000",
        "updated": "2021-11-06T15:37:10.472+0000"
    }
]

//Update Achievement
POST  http://localhost:8082/achievement/update

body>formdata:

currentDisplayName: MasterUltraGiga
displayName: GigatronProdavnica
description: Jkljfkdslfksldfj
displayOrder: 12
gameId: 111

result:
{
    "achievementId": ";dr}=>u4Q\\qrVB;{f",
    "displayName": "GigatronProdavnica", //changed displayName
    "description": "Jkljfkdslfksldfj", //changed
    "icon": "https:/robohash.org/MasterUltraGiga",
    "displayOrder": 13,
    "created": "2021-11-06T15:37:10.472+0000",
    "updated": "2021-11-06T15:46:12.721+0000" //date update changed
}

 //Delete Achievement
 
 DELETE  http://localhost:8082/achievement/delete/21

 result:

 {
    "timeStamp": "11-06-2021 04:55:38",
    "httpStatusCode": 200,
    "httpStatus": "OK",
    "reason": "OK",
    "message": "Achievement has deleted ! - GameApp"
}










