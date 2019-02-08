# ProjectLighthouse
A game running on the University of Kiel's highriser.

## UI Architecture
The application implements a variant of the well-known MVC pattern. The responsibilities are as follows:

### Responders
* handle user input

> Responders usually process a "higher-level" representation of user input (which is closer to the actual domain) rather than raw mouse events.

### View Controllers
Loosely speaking, view controllers coordinate communication between UI components. They...

* assemble the subview hierarchy (by adding childs etc.)
* communicate with external views (such as the lighthouse)
* mediate controller information

> Note that views and/or controllers may be polymorphic (i.e. they implement an interface such as `GridView`).

### Views
* present the model
* notify responders about events

### Controllers
* implement a responder for associated views (they accept user input)

### Models
* contain data and domain logic

> Models are completely independent from the UI code.
