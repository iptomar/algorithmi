FUNCTION TEXT inverte ROUND_OPEN TEXT txt ROUND_CLOSE
        ITERATE INTEGER i1 FROM 0 TO ELEMENTS_OF ROUND_OPEN txt ROUND_CLOSE DIV 2 STEP 1
            DEFINE TEXT aux SET txt[i1]
            EXECUTE txt SQUARE_OPEN i1  SQUARE_CLOSE SET txt[elementsOf(txt) - i1 - 1]
            EXECUTE txt SQUARE_OPEN elementsOf(txt) - i1 - 1  SQUARE_CLOSE SET aux
        END ITERATE
RETURN txt
    END inverte
