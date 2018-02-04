# nanodegreeandroid
Course Nanodegree Udacity Android Developer

Here are all the project, created in the course:


This is project about the course nanodegrees android develper first fase, 
I build a aplication android 
call it  the movie I  take  a data  from webservice and put the data in a view holder, 
 I used RecycleView and custom adapter, I personalized a toolbar with the itens menu: search, upcoming, top rated, inportrait the layout have 2 card in landscape has 4
I check the signal with internet and if not exist I show a activity the error 

minSdkVersion 14
targetSdkVersion 26


DON`T FORGET PUT YOUR API KEY IN:
gradle.properties 
API_KEY = “digit your number”
before this put in build.gradle (module:app)
buildTypes.each {
    it.buildConfigField( 'String', 'MY_API_KEY', API_KEY)
}

The image to splash
https://www.androidcentral.com/sites/androidcentral.com/files/postimages/21101/lloyd1.png



Api : Retrofit 2
http://square.github.io/retrofit/

URl`s WebService:
https://www.themoviedb.org/
for search:
https://api.themoviedb.org/3/search/movie?api_key=YOUR_KEY&query=it
for the popular movie:
http://api.themoviedb.org/3/movie/popular?api_key=YOUR_KEY
for the popular upcoming:
http://api.themoviedb.org/3/movie/upcoming?api_key=YOUR_KEY
for the popular top rated:
http://api.themoviedb.org/3/movie/top_rated?api_key=YOUR_KEY

I used this video for help-me
https://www.youtube.com/watch?v=qt3WCP-_uaY
https://www.youtube.com/watch?v=OOLFhtyCspA
https://www.youtube.com/watch?v=4Gd2PIzU3zE

Tak`s for help-me:
Jilies Ragonha
Denis Baptista Rosa
