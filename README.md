Itunes Albums
=============
This project was build for learning perposes. I took iTunes Open Api for display albums and music. 

Introduction
--------------
For creating this project i took MVVM pattern architecture with [Android Jetpack official cite](https://developer.android.com/jetpack) with Room, LiveData, Navigation, Data Binding and etc. First of all i draw some fragments.xml and get something like this. The main goal was to show artplay of all albums, name of the group and name of the album, and year(i think...). I must implement sort by group name, search by current group by name, and show additional information about current album like cost, music and etc.

<img src="https://sun9-24.userapi.com/c856124/v856124136/209628/lT6F_oN-3L4.jpg" height="400" width=auto> <img src="https://sun9-23.userapi.com/c857228/v857228136/115b1a/T0DigfgQ4-c.jpg" height="400" width=auto>

Implementation
----------------
Like i already said, first draw, next create View Models of all albums. I need LiveData with albums, for this i create repository which collect data from network and local database Room. Room contains Data Access Object(Dao) and Database with Entities. All heavy actions controls by Kotlin Coroutines on background threads for non blocking UI thread. For additional functions you can delete all albums and songs from database and delete current songs in the album. 

<img src="https://sun9-45.userapi.com/c857216/v857216136/111fc3/wGga2gvxoVE.jpg" height="400" width=auto>


All network actions works with Retrofit2 based on [iTunes Open Api](https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#/apple_ref/doc/uid/TP40017632-CH5-SW1), i had to understand how to get albums by names and get additional information like price, songs and etc. And how to search by group name.  All background works based on Kotlin Coroutines with Jobs. <br>

First problem solved by:  https://itunes.apple.com/search?term=out&entity=album&media=music&attribute=albumTerm <br>
Second problem solved by: Example: https://itunes.apple.com/lookup?id=982690853&entity=song&media=music <br>
But the main problem with this Api is that we can not get popular albums and solution for first search is type just random numbers or words.
