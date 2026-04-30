# Coding Guidelines and Conventions

> **IMPORTANT**
> You must follow the documented decisions stated in the files under the `adr/` subfolder.
> You must follow the documented conventions stated in this file.
> You must follow the documented conventions stated in the files under the `conventions/` subfolder.


This file contains the coding guidelines and conventions for this project. The main rules and goals are described in the [README.md](README.md) file, which also must be considered during code generation.

## System Design

* `tagessieg` is github hosted application that extracts data from opened issues, stores them in `data/` files and uses static site generation to provide a single page application (or single index.html file) displaying various statistics.
* It has a very limited target audience, basically just the two players `J` and `H` 
* Everything works directly on github, so the user interface for providing data are github issues and teh extractor and generator are triggered via github actions
* `tagessieg` offers two main use cases:
  * Collect Data
  * Show Statistics

### Use case: Collect Data

* When players start a new `day`, they open a new issue on the github repo. They will use a dummy user that only exists to create these issues.
* When the match is finished (three games total), the issue is submitted, which triggers a github action for the data extractor, which 
  * reads the issue and extracts the data
  * stores the data in the `data/` directory by appending it to an existing `yyyy.csv` file (or creates a new one after new-year's eve).

### Use case: Show Statistics

* A static site generator reads the `data/` directory and calculates various statistics.
* The statistics are listed in the README.md file.

## Privacy and Security

* The only two players are named `J` and `H`, there is no need to use any real names. If real names are used somewhere , this was done by accident, replace them with `J` and `H`.

## Language

* The users are german only, so any user facing component (form, template, site) will be german
* Internally, we use english language for issues, documentation, readme, and bot communication. 
* All dates follow german locale conventions. When displayed to the user, this means `dd.MM.yyyy`, when used in code, data or filenames, this means `yyyy-MM-dd`.

## Temp Folder

The `_tmp/` folder is used to stash files, it is ignored by git via global gitignore. You can read the directory content, but never delete it!
