.data
        error_arrayOutOfBounds : .asciiz "ERREUR: arrayOutOfBounds lors de l acces a un tableau, ligne : "
        ask_user_input : .asciiz "Lecture d'un nombre... "
.text
main :
        move $s7, $sp
        #allocation de la mémoire pour les déclarations de variables
        add $sp, $sp, -48

        #x = 5
        li $v0, 5
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, 0($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

        #y = x
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

        #x = 7
        li $v0, 7
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, 0($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

        #ecrire y
        lw $v0, -4($s7)
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

#pour x de 0 .. 9 repeter
    # affectation : tabDix[x] := ((x + 1) *  - 2)
#}
        li $v0, 0
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 9
        add $sp, $sp, 4    # Depile v0
        lw $t0, ($sp)
        move $t1, $v0
        bgt $t0, $t1, finPour0
        la $a0, 0($s7)
        sw $t0, ($a0)
        move $v0, $t1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        debutPour0:
        lw $t0, ($a0)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        bgt $t0, $v0, finPour0
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
                #tabDix[x] = ((x + 1) *  - 2)
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 1
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        add $v0, $v0, $v1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        mulu $v0, $v0, -1  # inverse le resultat entier
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        mulu $v0, $v0, $v1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, 0($s7)

        la $a0, -8($s7)
        # Verifie si l acces est bien dans les bornes du tableau : tabDix
        li $t2, 14
        li $v1, 10
        bge $v0, $v1, arrayOutOfBounds  # si indice > tableau.length -> erreur
        li $v1, 0
        blt $v0, $v1, arrayOutOfBounds  # si indice <= 0 -> erreur
        li $a1, 4      #
        mulu $v0, $v0, $a1  # Calcul de l indice
        sub $a0, $a0, $v0
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)


        la $a0, 0($s7)
        lw $t0, ($a0)
        addi $t0, $t0, 1
        sw $t0, ($a0)
        j debutPour0
        finPour0:
        move $t1, $v0
        li $v0, 0
        bgt $v0, $t1, jamaisDansPour0
        la $a0, 0($s7)
        lw $t0, ($a0)
        subi $t0, $t0, 1 #Soustrait 1 à x car le pour va 1 itération trop loin
        sw $t0, ($a0)
        jamaisDansPour0:
        #ecrire tabDix[y]
        lw $v0, -4($s7)

        la $a0, -8($s7)
        # Verifie si l acces est bien dans les bornes du tableau : tabDix
        li $t2, 17
        li $v1, 10
        bge $v0, $v1, arrayOutOfBounds  # si indice > tableau.length -> erreur
        li $v1, 0
        blt $v0, $v1, arrayOutOfBounds  # si indice <= 0 -> erreur
        li $a1, 4      #
        mulu $v0, $v0, $a1  # Calcul de l indice
        sub $a0, $a0, $v0
        lw $v0, ($a0)
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

#pour x de 0 .. 0 repeter
    #ecrire : x
#}
        li $v0, 0
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 0
        add $sp, $sp, 4    # Depile v0
        lw $t0, ($sp)
        move $t1, $v0
        bgt $t0, $t1, finPour1
        la $a0, 0($s7)
        sw $t0, ($a0)
        move $v0, $t1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        debutPour1:
        lw $t0, ($a0)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        bgt $t0, $v0, finPour1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
                #ecrire x
        lw $v0, 0($s7)
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall


        la $a0, 0($s7)
        lw $t0, ($a0)
        addi $t0, $t0, 1
        sw $t0, ($a0)
        j debutPour1
        finPour1:
        move $t1, $v0
        li $v0, 0
        bgt $v0, $t1, jamaisDansPour1
        la $a0, 0($s7)
        lw $t0, ($a0)
        subi $t0, $t0, 1 #Soustrait 1 à x car le pour va 1 itération trop loin
        sw $t0, ($a0)
        jamaisDansPour1:
#pour x de 1 .. 2 repeter
    #ecrire : x
    #pour y dans x .. (x * 2) repeter
    #ecrire : (x * y)
#}
#}
        li $v0, 1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        add $sp, $sp, 4    # Depile v0
        lw $t0, ($sp)
        move $t1, $v0
        bgt $t0, $t1, finPour2
        la $a0, 0($s7)
        sw $t0, ($a0)
        move $v0, $t1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        debutPour2:
        lw $t0, ($a0)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        bgt $t0, $v0, finPour2
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
                #ecrire x
        lw $v0, 0($s7)
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

#pour y de x .. (x * 2) repeter
    #ecrire : (x * y)
#}
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        mulu $v0, $v0, $v1
        add $sp, $sp, 4    # Depile v0
        lw $t0, ($sp)
        move $t1, $v0
        bgt $t0, $t1, finPour3
        la $a0, -4($s7)
        sw $t0, ($a0)
        move $v0, $t1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        debutPour3:
        lw $t0, ($a0)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        bgt $t0, $v0, finPour3
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
                #ecrire (x * y)
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        mulu $v0, $v0, $v1
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall


        la $a0, -4($s7)
        lw $t0, ($a0)
        addi $t0, $t0, 1
        sw $t0, ($a0)
        j debutPour3
        finPour3:
        move $t1, $v0
        lw $v0, 0($s7)
        bgt $v0, $t1, jamaisDansPour3
        la $a0, -4($s7)
        lw $t0, ($a0)
        subi $t0, $t0, 1 #Soustrait 1 à y car le pour va 1 itération trop loin
        sw $t0, ($a0)
        jamaisDansPour3:

        la $a0, 0($s7)
        lw $t0, ($a0)
        addi $t0, $t0, 1
        sw $t0, ($a0)
        j debutPour2
        finPour2:
        move $t1, $v0
        li $v0, 1
        bgt $v0, $t1, jamaisDansPour2
        la $a0, 0($s7)
        lw $t0, ($a0)
        subi $t0, $t0, 1 #Soustrait 1 à x car le pour va 1 itération trop loin
        sw $t0, ($a0)
        jamaisDansPour2:
        #ecrire (x -  - y)
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, -4($s7)
        mulu $v0, $v0, -1  # inverse le resultat entier
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        sub $v0, $v1, $v0
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

#tant que (5 < 2) faire :
    #ecrire : 100
#}
        tantQue4:
        li $v0, 5
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        slt $v0, $v1, $v0  #Si 5 < 2 alors $v0 = 1, sinon $v0 = 0
        beq $v0, 0, finTantQue4
                #ecrire 100
        li $v0, 100
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall


        j tantQue4
        finTantQue4:
        #x = 1
        li $v0, 1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, 0($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

        #y = 5
        li $v0, 5
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

#tant que (x < y) faire :
    #si (((x * 2) < y)) alors
    #ecrire : (y - (x * 2))
    # affectation : y := (y - 1)
#} sinon
    # affectation : y := (y + 3)
    #tant que ( (x < y) ) repeter
    #ecrire : x
    # affectation : x := (x + 1)
#}
#}
#}
        tantQue5:
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        slt $v0, $v1, $v0  #Si x < y alors $v0 = 1, sinon $v0 = 0
        beq $v0, 0, finTantQue5
        #si (((x * 2) < y)) alors
    #ecrire : (y - (x * 2))
    # affectation : y := (y - 1)
#} sinon
    # affectation : y := (y + 3)
    #tant que ( (x < y) ) repeter
    #ecrire : x
    # affectation : x := (x + 1)
#}
#}
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        mulu $v0, $v0, $v1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        slt $v0, $v1, $v0  #Si (x * 2) < y alors $v0 = 1, sinon $v0 = 0
        beq $v0, 0, else6
                #ecrire (y - (x * 2))
        lw $v0, -4($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 2
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        mulu $v0, $v0, $v1
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        sub $v0, $v1, $v0
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

        #y = (y - 1)
        lw $v0, -4($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 1
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        sub $v0, $v1, $v0
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)


        j endif6
        else6:
                #y = (y + 3)
        lw $v0, -4($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 3
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        add $v0, $v0, $v1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)

#tant que (x < y) faire :
    #ecrire : x
    # affectation : x := (x + 1)
#}
        tantQue7:
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        lw $v0, -4($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        slt $v0, $v1, $v0  #Si x < y alors $v0 = 1, sinon $v0 = 0
        beq $v0, 0, finTantQue7
                #ecrire x
        lw $v0, 0($s7)
        move $a0, $v0
        li $v0, 1
        syscall

        #retour a la ligne
        li $v0, 11
        li $a0, '\n'
        syscall

        #x = (x + 1)
        lw $v0, 0($s7)
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        li $v0, 1
        add $sp, $sp, 4    # Depile v0
        lw $v1, ($sp)
        add $v0, $v0, $v1
        sw $v0, ($sp)   # Empile v0
        add $sp, $sp, -4
        la $a0, 0($s7)
        add $sp, $sp, 4    # Depile v0
        lw $v0, ($sp)
        sw $v0, ($a0)


        j tantQue7
        finTantQue7:

        endif6:

        j tantQue5
        finTantQue5:
        j end

        # Erreur quand on tente d acceder a un indice non comprit dans les bornes d un tableau
        arrayOutOfBounds :
                li $v0, 4
                la $a0, error_arrayOutOfBounds
                syscall
                li $v0, 1
                move $a0, $t2
                syscall

#code de fin
end :
        li $v0, 10
        syscall
