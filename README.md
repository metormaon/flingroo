# flingroo

BNF is declared as follows:

```groovy
    BNF grammar = BNF.bnf(E) {
        E <- T1 + T2 + ~F
        F <- ~(G | T3)
    }
```

Where:

* The first parameter of bnf() is the root variable.

* You can omit the root variable (and its parentheses), and the system will try to find it by topological sort (not implemented yet)

* A variable is assigned a statement made of variables and terminals, by a <- operator

* Due to the trick used for creating the operator <-, the statement that follows it must be put in parentheses

* Variables or terminals are concatenated by the + operator

* The | operator, of which precedence is lesser than +, provides multiple alternatives for variable and terminal sequences

* Optional variables are prefixed with ~

* Variables and terminals are enums. Terminals may get their corresponding string images. 

```groovy
    static enum V implements Variable {E, F, G}
    static enum T implements Terminal {T1, T2("%%"), T3
        T() {}
        T(String label) {
            this.label = label
        }
    }
```

* It is recommended to statically import the members of V and T.

* When printed, the following BNF looks like this:

```
    E <- ((T1 df) (F)?)
    F <- ((G | T3))?
```

* The class BNFParser parses typical BNF syntax:

```
<class declaration> ::= <class modifiers>? class <identifier> <super>? <interfaces>? <class body>
``` 

  into flingroo's BNF syntax. 
  
* Currently it parses the Java syntax specified in the resource __java-bnf.txt__

* The resulting JavaBNF.groovy was manually saved in the gen-test folder

* Please note: the provided Java syntax, copied from somewhere on the web (credited in the file) is not perfect, and therefore the syntax doesn't perfectly compile. But it's relatively close to that. 

* Unit tests and documentation will be added later.