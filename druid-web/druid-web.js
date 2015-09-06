//Define Collections
PatientRecords = new Mongo.Collection("patients");


if (Meteor.isClient) {
  // This code only runs on the client
  Template.body.helpers({
    questionnaireTexts: [
      { 
        question: "Name",
        tag: "name"
      },
      {
        question: "Age",
        tag: "age"
      },
      {
        question: "Specify Therapy Level",
        tag: "therapy"
      }
    ]
  });
  
  // Submit Events
Template.body.events({
    "click input#form-submit": function (event) {
      // Prevent default browser form submit
      event.preventDefault();
 
      // Get value from form element
      var name = document.getElementById('name').value;
      var age = document.getElementById('age').value;
      var therapy = document.getElementById('therapy').value;
      var id = name.split(' ').join('_');;
      // Insert a task into the collection
      PatientRecords.insert({
        _id: id,
        name: name,
        owner: Meteor.userId(),
        age: age,
        therapy: therapy,
        createdAt: new Date() // current time
      });
 
      // Clear form
      document.getElementById('name').value = '';
      document.getElementById('age').value = '';
      document.getElementById('therapy').value = '';
    }
  });

}

if (Meteor.isServer) {
  Meteor.startup(function () {
    // code to run on server at startup
    collectionApi = new CollectionAPI({ authToken: undefined });
    collectionApi.addCollection(PatientRecords, 'patients');
    collectionApi.start();
  });
}
