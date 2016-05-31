# Lettrine

Lettrine is a library that let you display TextView with an [intial](https://en.wikipedia.org/wiki/Initial) first letter. 

## Features

* Select how many lines your initial should use
* Choose the text font
* Change the text size
* Change the text color
* Display Spanned text

## Examples

The following screenshots illustrate the library capabilities listed above :

| <img style="float: center;" src="http://i.imgur.com/ivgH1gZ.jpg" width="400">  | <img style="float: right;" src="http://i.imgur.com/hHVzvXR.jpg" width="400">  | <img style="float: right;" src="http://i.imgur.com/6RL4AeR.jpg" width="400">  | <img style="float: right;" src="http://i.imgur.com/3MsIm3t.jpg" width="400">  | <img style="float: right;" src="http://i.imgur.com/VuaW6pA.jpg" width="400">  |
|:-------:|:------:|:------:|:------:|:------:|
| Lettrine with a thre lines height | Lettrine with a thre lines height | Font customization | Html balisis inclusion | Custom text color |

## Including in your project

Here is an example of how you can a LettrineTextView in your project :

      <com.github.rpradal.lettrine.LettrineTextView
                  android:layout_width="fill_parent"
                  android:layout_height="wrap_content"
                  app:lettrine_textColor="@android:color/holo_red_dark"
                  app:lettrine_text="Lorem ipsum"
                  app:lettrine_lettrineSize="3"
                  app:lettrine_textSize="14sp" />
                  
The exhaustive attribute list of a LettrineTextView is the following :

* _lettrine_textSize_  Body text size
* _lettrine_lettrineSize_ Number of body lines the lettrine will take
* _lettrine_font_ The font to use (applied to both lettrine letter and body text)
* _lettrine_textColor_ Text color (applied to both lettrine letter and body text)
* _lettrine_text_ Text to display in the LettrineTextView

Of course all these attributes are settable programatically thanks to public methods.

## Configuration

Add the following line in your gradle _dependencies_ block.

      compile 'com.github.rpradal.lettrine:lettrine:release_number'

 _release_number_ has to be replaced with the current lettrine version which can be found in the github interface.

## License

Project developed with [LeMonde.fr](http://www.lemonde.fr/) and [Octo Technology](http://www.octo.com) support.

  
 
| <img style="float: center;" src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Lemonde_fr_2005_logo.svg/800px-Lemonde_fr_2005_logo.svg.png" width="400">  | <img style="float: right;" src="http://directory.facci.com.au/image/company_logo/full_2495.png" width="400">  |
|-------:|:------:|




      Copyright 2016 Le Monde.fr 

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.`
