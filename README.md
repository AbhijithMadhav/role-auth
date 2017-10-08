
## Assumptions
* Assumed that id's are strings. The models can be parameterized to make the ids more generic
* There are no hierarchical relationships beetween roles. 
* Thread safe but no concurrent reads


## Notes
Broadly there is a layered design with models, services and data access objects.

Currently the service layer is mostly wrappers around dao calls but can be used later to implement business logic.

THe choice of using an in memory datastructures for dao layer is not desirable. This is because the entities have relationships between them which require mapping validations. I have had to, at a primitive level, implement relationship validations(to ensure references remain accurate). Also as everything in java is reference, I've had to deep copy objects to store them separately as in a typical storage solution. This has related in an API which clients have to carefully implement to get things correct.

Working with a relational db, say even SQLlite, would have been more worthwhile as it would have taken care of the above concerns. 

## Building and running
Unzip or clone the source(..)
```bash
$ cd role-auth-master
$ gradle build # or ./gradlew build
$ gradle run # Executes for default input file
```

To execute for other inputs
```bash
$ cd build/distributions
$ unzip role-auth-1.0.zip
$ cd role-auth-1.0/bin/
$ ./role <input-file-path>
```

## Input file format
Input file can be composed using the below types of inputs
* Resource definition
* Role definition
* User definition and role assignment
* Access query

Refer to the illustration for syntax

```
Re1 : A1 A2 A3      # Resource definition. Resource name has to start with 'Re'. Action definition is implicit
Re2 : A1 A4 A5
Ro1 : Re1 A3		# Role definition. Role name has to start with a 'Ro'. Resource and action mapping must have been established previously thourough a resource definition
Ro1 : Re1 A2
Ro2 : Re2 A4
Ro3 : Re1 A5
U1 : + Ro1          # Role assignment. User definition is implicit. User name has to start with 'U'. Role must be existing. 
U2 : + Ro2			# '+' is adding roles
U1 : Re1 A3			# Access query. Does U1 have A3 access on Re1
U2 : Re1 A2
U2 : + Ro1
U2 : Re1 A2
U1 : - Ro2          # '-' is removing roles
U1 : Re2 A4
```
