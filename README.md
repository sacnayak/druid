# druid
Hack for PennApps XII

Inspiration:
How can you convince a paralyzed person who is unable to lift the lightest of objects, that everything will be okay soon? Verbal encouragement can only go so far. The individual will be motivated to exercise and get better if he/she can visualize a path towards being fully functional. Enter DRUID.

What it does:
- We envision DRUID as an immersive rehabilitation assistant for people who suffer from loss of motor function due to stroke or injury. By gamifying an otherwise boring and mundane regimen of exercises, DRUID enables patients to take charge of their own recovery, under the trained eyes of their physiotherapist.
- DRUID offers a gamified VR experience in which a patient's limited motor skills, captured by a Myo band, are exaggerated to offer positive reinforcement in rehabilitation.

How we built it:
- We quickly built a web portal on the MeteorJS framework to act as an input for the physiotherapist to define a physio routine for a given patient. The physiotherapists therapy plan is then stored in a MongoDB, hosted freely on https://druid.meteor.com
- On the patient's end, we built an Android application paired with a Myo armband and a Unity3D game. We programmed the game to respond to a patient's exercise routine input through the Myo.

Challenges we ran into:
- One of the primary challenges we faced that our team was inexperienced in VR, cardboard, Unity3D and even MeteorJS. Overcoming the learning curves quickly and hacking our way through was a big challenge.
- Myo has an inbuilt plugin for Unity on Mac/Windows & iOS. We built it on Android, overcoming a very hazily documented area of Unity <-> Android <-> Myo.
- Integration of so many platforms together in 36hrs to build DRUID.

Accomplishments that we're proud of:
- Overcoming steep learning curves quickly.
- Having an end-to-end working prototype of our vision.
- Helping each other out through unknowns.

What we learned:
- Approaching the problem from multiple perspectives - Value addition for the user, Intuitive interfaces, technical considerations
- Working on multiple hardware/software platforms. Was challenging, but gave us the confidence to integrate cross-platform solutions so as to cater to a holistic user experience
- First time hackathon experience for half the team. Inspiring and exciting to see so many fellow hackers working on so many cool ideas

What's next for Druid:
- We wanted to build a lot more into DRUID. Our vision for DRUID is for it to evolve into a fun, motivational and personalized medium for people to undergo paralytic rehabilitation treatment. We wholly acknowledge that no single type of motivation works for everyone, and we would like our game to be adaptive to the patient's recovery. An important focus would be to design games that let patients operate effectively in a gamified VR experience, that would empower them emotionally as well as physically in the rehab routine. The games intend to encompass and help the patient achieve a sense of accomplishing a day to day task involving his/her arm movements. The demo example correlates the patient's arm-pitching motion (about the elbow) with the task of lifting 3D objects. Output on the VR proportionate to the patient's physical effort gives him/her a better sense of satisfaction than plain physical therapy. Some of the future enhancements that we plan to build on top of our hack:
- Addition of a variety of games corresponding to real-world exercises that can be augmented with the help of a Myo armband
- A dashboard for the physio to track, and modify patients routine based on continuous input from patient's performance in routine
- VR assistance to train patient on new exercises and routine
- Adaptive difficulty to help patient progressively improve before next visit to the physio
- Future projection of recovery based on patient's performance in present and past routines

Built With
- google-cardboard
- myo arm-band
- android
- meteor.js
- Unity
