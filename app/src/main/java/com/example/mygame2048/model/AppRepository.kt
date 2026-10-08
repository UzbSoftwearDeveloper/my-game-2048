package com.example.mygame2048.model


class AppRepository {
    private var count = 0
    fun getSum() = count
    fun setSum(count : Int){
        this.count = count
    }
    var matrix = arrayOf(
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0)
    )
        private set
    var oldMatrix = arrayOf(
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0),
        arrayOf(0,0,0,0)
    )
    private val newElement = 2
    fun addNewElement(){
        val list = ArrayList<Pair<Int, Int>>()
        for (i in 0 until 4){
            for (j in 0 until 4){
                if (matrix[i][j] == 0){
                    list.add(Pair(i,j))
                }
            }
        }
        if (list.isNotEmpty()) {
            val random = list.random()
            matrix[random.first][random.second] = newElement
        }
    }
    init {
        addNewElement()
        addNewElement()
    }
    fun moveUp(){
        val temp = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        for (i in 0 until 4){
            var isAdded = false
            val ls = ArrayList<Int>()
            for(j in 0 until 4){
                if (matrix[j][i] == 0) continue
                if (ls.isEmpty()){
                    ls.add(matrix[j][i])
                }else{
                    if (ls.last() == matrix[j][i] && !isAdded){
                        ls[ls.lastIndex] = ls.last()*2
                        count += ls[ls.lastIndex]
                        isAdded = true
                    }else{
                        ls.add(matrix[j][i])
                        isAdded = false
                    }
                }

            }
            for (j in 0 until 4){
                if (j < ls.size){
                    temp[j][i] = ls[j]
                }
            }
        }

        matrix = temp
        addNewElement()
    }
    fun moveDown(){
        val temp = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        for (i in 0 until 4){
            var isAdded = false
            val ls = ArrayList<Int>()
            for(j in 3 downTo 0){
                if (matrix[j][i] == 0) continue
                if (ls.isEmpty()){
                    ls.add(matrix[j][i])
                }else{
                    if (ls.last() == matrix[j][i] && !isAdded){
                        ls[ls.lastIndex] = ls.last()*2
                        count += ls[ls.lastIndex]
                        isAdded = true
                    }else{
                        ls.add(matrix[j][i])
                        isAdded = false
                    }
                }

            }
            var index = 0;
            for (j in 3 downTo 0){
                if (index < ls.size){
                    temp[j][i] = ls[index++]
                }
            }
        }

        matrix = temp
        addNewElement()

    }
    fun moveLeft(){
        val temp = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        for (i in 0 until 4){
            var isAdded = false
            val ls = ArrayList<Int>()
            for(j in 0 until 4){
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()){
                    ls.add(matrix[i][j])
                }else{
                    if (ls.last() == matrix[i][j] && !isAdded){
                        ls[ls.lastIndex] = ls.last()*2
                        count += ls[ls.lastIndex]
                        isAdded = true
                    }else{
                        ls.add(matrix[i][j])
                        isAdded = false
                    }
                }

            }
            for (j in 0 until 4){
                if (j < ls.size){
                    temp[i][j] = ls[j]
                }
            }
        }

        matrix = temp
        addNewElement()

    }
    fun moveRight(){
        val temp = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        for (i in 0 until 4){
            var isAdded = false
            val ls = ArrayList<Int>()
            for(j in 3 downTo 0){
                if (matrix[i][j] == 0) continue
                if (ls.isEmpty()){
                    ls.add(matrix[i][j])
                }else{
                    if (ls.last() == matrix[i][j] && !isAdded){
                        ls[ls.lastIndex] = ls.last()*2
                        count += ls[ls.lastIndex]
                        isAdded = true
                    }else{
                        ls.add(matrix[i][j])
                        isAdded = false
                    }
                }

            }
            var index = 0
            for (j in 3 downTo 0){
                if (index < ls.size){
                    temp[i][j] = ls[index++]
                }
            }
        }

        matrix = temp
        addNewElement()
    }
    fun getSumMatrix() : Int{
        var sum = 0
        for (i in 0 until 4){
            for (j in 0 until 4){
                sum += matrix[i][j]
            }
        }
        return sum
    }
    fun newMatrix(){
        matrix = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        addNewElement()
        addNewElement()
    }
    fun setCloneMatrix(){
        for (i in 0 until 4){
            for (j in 0 until 4){
                oldMatrix[i][j] = matrix[i][j]
            }
        }
    }
    fun getCloneMatrix(){
        for (i in 0 until 4){
            for (j in 0 until 4){
                matrix[i][j] = oldMatrix[i][j]
            }
        }
    }
    fun areaMatrixEqual(): Boolean{
        for (i in 0 until 4){
            for (j in 0 until 4){
                if (matrix[i][j] != oldMatrix[i][j]) return false
            }
        }
        return true
    }
    fun check(): Boolean{
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (matrix[i][j] == 0) return true
            }
        }
        for (i in 0 until 4){
            for (j in 0 until 3){
                if (matrix[i][j] == matrix[i][j+1]) return true
            }
        }
        for (i in 0 until 3){
            for (j in 0 until 4){
                if (matrix[i][j] == matrix[i+1][j]) return true
            }
        }
        return false
    }
}