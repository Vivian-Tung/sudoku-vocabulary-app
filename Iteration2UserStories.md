**First working version should implement functionality in accord with the user stories of iteration 1 and the TDD examples:**

**User Stories implemented from iteration 1:**

1) As a novice user, I want to be able to access a help page so that I can reference it when I don't
remember how to use the app.

- This is achieved by clicking the "?" symbol on the top right screen when clicking the button play. This button 
gives a quick overview of how the game of Sudoku is played and how our version with vocabulary instead of numbers work.

2) As a novice user, I want to be able to access the word bank so that I can study and
review the words before I play the game.

- Only one category has been implemented, to be finished in iteration 3

3) As a expert language learner, I want many categories of words such as foods, animals, travel, to choose from so that I can improve my vocabulary in chunks and become faster at categorizing the vocabulary.

- So far only the UI with buttons going to play the game and going back to the homescreen are implemented, to be finished in iteration 3.

4) As a language teacher, I want to be able to choose the words that were taught this week to reinforce the learning.

- Only one category has been implemented, to be finished in iteration 3.

5) As a language teacher, I want to be able navigate back to the history of wordlists ive created so I can use them for future cohorts of students.

- The UI is finished for this, to be functioning in iteration 3.

**User Stories to be implemented from iteration 1 to iteration 3:**

1) As a novice user, I want there to be a stopwatch timer so that I can see how long it takes for me to solve the sudoku.

- This will be displayed on the top of the screen or within some other button at the top of the screen for the user to check their time. The timer will pause when the user exits the game and resume when the user enters the game.  

2) As an expert user, I want to be able to save/load a game in progress so
so that I can stop and resume playing at my own convenience.

- This will also have a button at the top or be joint all in one button with the timer included. The user can click the save button to save their current progress in the game. 

3) As an expert user, I would like to be able to switch the look of the
application between light and dark themes so that I have a better
time using the application in different lighting conditions.

- This will be a toggle button with a "moon" symbol located at the top of the screen indicating light or dark mode. The user can press the button to switch between the different themes.  

4) As a beginner language learner, I want a list of vocabulary words that I keep getting wrong so that I can focus more on the mistaken words to improve my knowledge.

- This will appear in the word bank option from the home menu with a button named "mistaken words". The user can click on the button to view words that they constantly get wrong. 

5) As a medium language learner, I want the option of having a "favourite" option for the vocabulary words
so that I can refer back to it. For example, clicking the unfilled in bookmark beside the word will put it into the bookmarked words page.

- This will also be happening in the word bank option from the home menu, buttons will have the bookmark symbol and when clicking it, it will be saved in a favorite button.

6) As a language teacher, I want a pronunciation feature for the words so my students could learn how to say it.
- This will be a feature on the pop-up screen when pressing a cell on the Sudoku board. It will probably have the speaker symbol which then produces a text to speech audio when clicking the symbol.  


**Add user stories and examples for the following additional requirements, with a plan to implement these in iteration 3:**

****Different Devices****

As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice, so that the words will be conveniently displayed in larger, easier to read fonts.

- The sudoku grid will automatically scale when it detects a tablet device. 

As a vocabulary learner taking the bus, I want to use my phone in landscape mode for Sudoku vocabulary practice, so that longer words are displayed in a larger font than standard mode.

- The sudoku grid will automatically rotate and scale when it detects landscape orientation 

As a vocabulary learner always on the move, I want to save my progress on my phone/tablet for Sudoku vocabulary practice, so that I can resume my progress at a later time.

- This will appear as a save button at the top of the screen. The user can click the save button to save their current progress in the game. 

As a vocabulary learner practicing late at night at home, I want to use my phone/tablet in dark mode for Sudoku vocabulary practice, so that my eyes dont feel strained from the bright colours of app.

- This will be a toggle button with a "moon" symbol located at the top of the screen indicating light or dark mode. The user can press the button to switch between the different themes.  

****Different Size Sudoku Grids****

As a teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3).

- After user chooses their word category, the user can also choose their preferred grid size with two options of 4x4 or 6x6 and the game will appear accordingly. 

As a vocabulary learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4).

- After user chooses their word category, the user can also choose the grid size the game will appear accordingly. 
