.data
	newline : .asciiz "\n" #caractère ascii représentant le retour à la ligne
.text
main :
	move $s7, $sp
	#allocation de la mémoire pour les déclarations de variables
	add $sp, $sp, -4

	#x = 5
	li $t0, 5
	sw $t0, 0($s7)

	#ecrire x
	li $v0, 1
	lw $t0, 0($s7)
	move $a0, $t0
	syscall

	#retour à la ligne
	li $v0, 4
	la $a0, newline
	syscall

	#x = 7
	li $t0, 7
	sw $t0, 0($s7)

	#ecrire x
	li $v0, 1
	lw $t0, 0($s7)
	move $a0, $t0
	syscall

	#retour à la ligne
	li $v0, 4
	la $a0, newline
	syscall