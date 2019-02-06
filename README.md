# ProjectLighthouse
A game running on the University of Kiel's highriser.

## UI Architecture
The application implements a variant of the well-known MVC pattern. The responsibilities are as follows:

### Responders
* handle user input events

### View Controllers
Loosely speaking, view controllers "manage" a single or multiple views. They...

* assemble the subview hierarchy (by adding childs etc.)
* implement a responder for associated views
* communicate with external views (such as the lighthouse)

> Note that views may be polymorphic (i.e. they implement an interface such as `GridView`)

### Views
* present the model
* notify responders about events

### Models
* contain data and domain logic
