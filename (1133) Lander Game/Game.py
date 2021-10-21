import turtle as tr, \
    math as m, \
    random as r

class Lander(tr.Turtle):

    '''
    Purpose: Lander represents the lander object in the game
    Instance variables: vx=y value of initial position, yvel=y value of initial velocity,
                        fuel: keeps track of fuel used
    Methods: __init__: inherits init from turtle and initialized the turtle object
             move: simulate gravity by moving the object down by a certian negative velocity
             thrust: moves the lander in the direction it is facing
             left: move lander left 10 if fuel is avalible
             right: move lander right 10 if fuel is avalible
    '''

    def __init__(self,xpos,ypos,xvel,yvel):
        tr.Turtle.__init__(self)
        self.vx = xvel
        self.vy = yvel
        self.fuel = 50
        self.ht()
        self.shape("arrow")
        self.seth(90)
        self.pu()
        self.speed(0)
        self.setpos(xpos, ypos)
        self.st()

    def move(self):
        self.vy -= 0.0486
        self.setpos((self.xcor()+self.vx),(self.ycor()+self.vy))

    def thrust(self):
        if done == False:
            if self.fuel > 0:
                self.fuel -= 1
                ang = m.radians(self.heading())
                self.vx += m.cos(ang)
                self.vy += m.sin(ang)
                self.clear()
                print(self.fuel)
                self.write(self.fuel)
            else:
                self.clear()
                print('Out of fuel')
                self.write('Out of fuel')

    def left(self):
        if done == False:
            if self.fuel > 0:
                self.seth(self.heading() + 10)
                self.fuel -= 1
                self.clear()
                print(self.fuel)
                self.write(self.fuel)
            else:
                self.clear()
                print('Out of fuel')
                self.write('Out of fuel')

    def right(self):
        if done == False:
            if self.fuel > 0:
                self.seth(self.heading() - 10)
                self.fuel -= 1
                self.clear()
                print(self.fuel)
                self.write(self.fuel)
            else:
                self.clear()
                print('Out of fuel')
                self.write('Out of fuel')

class Meteor(tr.Turtle):

    '''
    Purpose: this represents a meteor object
    Instance variables: vy=y initial velocity
    Methods: __init__: inherits turtle init and creates the meteor turtle object
             move: simulate gravity by moving the object down by a certian nevative velocity
             contains: check to see if the object is close to a certian x,y position
    '''

    def __init__(self):
        red = 0
        green = 0
        blue = 0
        tr.Turtle.__init__(self)
        self.vy = 0
        self.pu()
        self.ht()
        self.speed(0)
        self.shape("circle")
        self.turtlesize(3)
        red = r.random()
        greed = r.random()
        blue = r.random()
        self.color(red,green,blue)
        self.setpos(r.uniform(100,900),1000)
        self.st()

    def move(self):
        self.vy -= 0.0486
        self.setpos((self.xcor()),(self.ycor()+self.vy))

    def contains(self,x,y):
        d = m.sqrt((x-self.xcor())**2+(y-self.ycor())**2)
        if d < 46:
            return True
        else:
            return False

class Game:

    '''
    Purpose: run the game and handle the end conditions
    Instance variables: i = keep track of the number of game loops
                        mtrlist = list of meter objects
                        player = Lander object created with the lander class
    Methods: __init__: set up the lander and counters along with monitoring the turtle window
             gameloop: does multiple things- spawns a meteor object every 33 function calls,
                       call the move function to move the player via gravity, check every
                       meteor in the mtrlist to check for a collision, check to see if the lander is
                       off of the screen, check to see if the lander is on the ground and if it is
                       going slow enough for a successful landing, then if all conditions are
                       false, run the game loop again in 30sec
    '''

    def __init__(self):
        self.i = 0
        self.mtrlist = []
        tr.setworldcoordinates(0,0,1000,1000)
        tr.delay(0)
        tr.bgcolor("orange")
        self.player = Lander(r.uniform(100,900),r.uniform(800,900),r.uniform(-5,5),r.uniform(-5,0))
        self.gameloop()
        tr.onkeypress(self.player.thrust, 'Up')
        tr.onkeypress(self.player.left, 'Left')
        tr.onkeypress(self.player.right, 'Right')
        tr.listen()
        tr.mainloop()

    def gameloop(self):
        global done
        done = False
        mtrcrash = False
        self.i += 1
        if (self.i % 33) == 0:
            mtr = Meteor()
            self.mtrlist.append(mtr)
        self.player.move()
        for mtr in self.mtrlist:
            mtr.move()
            if mtr.contains(self.player.xcor(), self.player.ycor()) == True:
                mtrcrash = True
            if mtr.ycor() < -50:
                mtr.ht()
                self.mtrlist.remove(mtr)
        if self.player.xcor() > 1000:
            self.player.setpos(1000, self.player.ycor())
        elif self.player.xcor() < 0:
            self.player.setpos(0, self.player.ycor())
        elif self.player.ycor() > 1000:
            self.player.setpos(self.player.xcor(), 1000)
        if self.player.ycor() < 10:
            if self.player.vx >= 2 or abs(self.player.vy) >= 5:
                self.player.clear()
                print('You crashed!')
                self.player.write('You crashed!', font=("Arial", 20, "normal"))
                done = True
            else:
                self.player.clear()
                self.player.write('Sucessful landing!', font=("Arial", 20, "normal"))
                print('Sucessful landing!')
                done = True
        elif mtrcrash == True:
            self.player.clear()
            print('You crashed!')
            self.player.write('You crashed!', font=("Arial", 20, "normal"))
            done = True
        else:
            tr.Screen().ontimer(self.gameloop, 30)

def main():
    Game()

if __name__ == '__main__':
    main()
