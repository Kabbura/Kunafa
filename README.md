# Kunafa
Easy to use, high level framework in Kotlin for front-end web-development. 
Allows for creating web apps without using HTML, CSS, or JavaScript.

## Philosophy
Web apps framework without using HTML, CSS, or JavaScript.

## Problem

* Web technologies are pain. Html is verbose, CSS is unexpected, and javascript is.. javascript.
* The no. of technoloies a developer needs to learn to write web apps is big. That is, HTML, CSS and JavaScript at the least. Then there are javascript frameworks (react, angular, vue), LESS, SCSS, Haml. There are also packaging tools, gulp, webpack.
* Modern javascript frameworks solve the JavaScript problem, not the front end problem. React use JSX (embeded HTML in javascript) while Angular requires CSS and HTML.

## Proposal
An easy to use, high level framework in Kotlin for web development. You do not need to learn the web stack, only the framework to be able to write web apps.

#### Developer experience
Developers only need to use Kotlin for developement. The framework the view from the presenter and data.

- You write the view (similar to Android xml layouts) in Kotlin DSL. e. g.

        verticalLayout {
         width = matchParent
         height = matchParent
         background = Color.rgb(240, 240, 240)
         Button {
          text = "Click me"
        }
       }

- The presenter and model (similar to Android activity or iOS ViewController) should extend a base class and implement certain life cycle functions.
- The framework views contains easy to understand and familiar components and layouts managers. i. e. Button, TextView, TextInput, HorizontalLayout, VerticalLayout and so on.
- The framework makes laying out objects easy, e. g.  match parent, wrap content.
- There should be a way to wrap any html, css, and js into a framework component to use it inside the framework.

### Implementation

- Kotlin transpiles to javascript, and is well designed to support DSLs.
- Code will be turned to javascript at compile time, and a basic HTML file loads the generated .js file.
- At runtime, the js file will generate the required HTML, and CSS files containing the who application.

---
## Getting started
### Prerequisite 
Before running Kunafa, make sure you have `npm` installed. To check if it installed, run 

    npm -v
      

### Installation
The following installation instructions are for linux/macOS.
1. Create a new directory

        mkdir ~/my-app
        cd ~/my-app
2. Clone this repo

        git clone https://github.com/Kabbura/Kunafa.git
        cd kunafa
 3. Run gradle
        
        ./gradlew -t run
4. The server now is running at http://localhost:8088/. Navigate to this address and you should see kunafa is running.

### Open in IntelliJ
You can open the directory of this repo in IntelliJ as a project. There you will see the project file structure. The main entry point of the app is the main function at
/src/main/kotlin/com/narbase/kunafa/app

---

Your feedback is most welcomed. Let us know how is your experience with Kunafa. 
