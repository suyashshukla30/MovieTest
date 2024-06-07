## Description

This Android application helps you explore movie information. 
It retrieves movie data from an API and stores it in a local database using Room. 
The app displays a list of movies with details like name, poster, year, and runtime.

## Test Cases
1. If app is opened first time with proper net connection: Then api is fetched in main screen and data is then parsed and stored in room. A ref. screen recording is attached.
   
https://github.com/suyashshukla30/MovieTest/assets/75569185/ac4d1f9d-3d9c-476b-bd45-a2b78f1937fc

2. If app already fetched data and stored in room, then in movielist activity, it will fetch from room even net is on. Screen recording is attached.

https://github.com/suyashshukla30/MovieTest/assets/75569185/e28e9656-c5ca-489d-8ff5-d7a8500a79e1

3. If net close and room filled, then fetching from room, screen recording is attached.

https://github.com/suyashshukla30/MovieTest/assets/75569185/cf305409-908c-4b44-baaa-ad0bb4ec8ad2

### App Drive link: https://drive.google.com/file/d/1-AXZJexd8kBNzcTN_VFMzcOVYYNSvPzZ/view?usp=sharing

### Note: 

In case of no internet a default image will be shown, cause image from the link that has been stored in room cannot be fetched, but the image that has been cached or loaded before will do appear.

#### App screenshot(main screen)

![WhatsApp Image 2024-06-07 at 11 41 25 AM](https://github.com/suyashshukla30/MovieTest/assets/75569185/dd4da1d4-328f-4665-be38-9b189c346d85)



