# Compilateur Plic

Compiler for an invented language named _Plic_, produce assembly code for MIPS processors.

It is written with in Java, the code is in French because it was a project for the school @IUT Nancy-Charlemagne.

## Plic grammar

The grammar is described in the pdf `GrammairePlic.pdf`.

### Languages features

- Statically Typed variables
- Basic mathematical operators : `+` `-` `*` `/`
- For loops and while loops
- If-then and if-then-else conditional statements
- Arrays, and arrayOutOfBounds errors checked at the time of compilation
- Bloc of code, with their own variable's scope
- User's inputs

## Example

There is an example in these files :

- `test_complet.plic` : the source program written in _Plic_
- `test_complet.asm` : the produced assembly code
