package org.example

import kotlin.math.max
import kotlin.math.min

fun main() {
    //threeSum(intArrayOf(-1,0,1,2,-1,-4))
    //var num = check(1,2,3,4,5,6,7,8,9,10)
    //println(num)
    //println(fizzBuzz(15))
    //val res = groupAnagrams(arrayOf("eat", "tea", "tan", "ate", "nat", "bat"))
    //println(longestConsecutive(intArrayOf(9,1,4,7,3,-1,0,5,8,-1,6)))
//    val nums = intArrayOf(0,1,0,3,12)
//    moveZeroes(nums)
//    println(nums.toList())
//    val height = intArrayOf(1,8,6,2,5,4,8,3,7)
//    println(maxArea(height))
//    val len = lengthOfLongestSubstring("abcabcbb")
//    print(len)
    val res = findAnagrams("abab", "ab")
    println(res)
}
fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort()

    //var index = nums.indexOf(nums.filter{ it > 0 }.get(0))
    for (i in nums.indices) {
        var endIndex = nums.size - 1
        for (j in i+1 until nums.size) {
            when (nums[i]+ nums[j]+ nums[endIndex] > 0) {
                true -> {
//                    for ()

                }
                false -> println(listOf(nums[i], nums[j], nums[endIndex]))
            }
        }
    }
    return listOf(listOf(1,2,3))
}
fun check(vararg arr:Int) : Boolean{
    var sum: Int = 0
    for (i in arr){
        sum += i
    }
    return sum == 0
}
fun fizzBuzz(n: Int): List<String> {
    val arr: IntArray = IntArray(n){it + 1}
    //arr.sortedWith()
    val result2: List<String> = arr.map { if (it % 3 == 0 && it % 5 == 0) "FizzBuzz" else if (it % 3 == 0) "Fizz" else if (it % 5 == 0) "Buzz" else it.toString()}

    return result2
}
fun groupAnagrams(strs: Array<String>): List<List<String>> {
    if (strs.isEmpty()) return listOf()
    val map = mutableMapOf<String, MutableList<String>>()
    strs.forEach {
        val sorted = it.toCharArray()
        sorted.sort()
        val str = String(sorted)
        if (map[str] == null) {
            map[str] = mutableListOf(it)
        } else {
            map[str]!!.add(it)
        }
    }
    return map.values.toList()
}
fun longestConsecutive(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    val sortedNums = nums.sorted()
    val smallest = sortedNums[0]
    var intArr: IntArray =  IntArray(sortedNums.size){it +smallest}
    var len: Int = 0
    var list: MutableList<Int> = mutableListOf()
    sortedNums.mapIndexed { index, value ->
        if (value == intArr[len] ) {
            len++
        }else if(value <= intArr[len]){
            len =len
        }
        else {
            list.add(len)
            intArr = intArr.copyOfRange(len+2, intArr.size)
            len = 0
        }
    }

    if(list.isEmpty()) {
        return len
    }else {
        if(len > list.maxOrNull()!!) {
            return len
    }
        return list.maxOrNull()!!
    }
}
//shaba 1
fun moveZeroes(nums: IntArray): Unit {
     fun swap(nums: IntArray, index: Int) {
        nums.forEachIndexed() { i, value ->
            if (  i >= index && i < nums.size - 1  ) {
                nums[i] = nums[i + 1]

            }
        }
        nums[nums.size - 1] = 0
    }
    nums.forEachIndexed() { index, value ->
        while (nums[index] == 0) {
            swap(nums, index)
        }
    }
}
fun maxArea(height: IntArray): Int {
    var left = 0
    var right = height.size -1
    var result = 0
    while (left != right){
        val width = right -left
        val hei = min(height[left],height[right])
        result = max(result, width * hei)
        if (height[left] < height[right]) left++ else right--
    }
    return result
}
fun lengthOfLongestSubstring(s: String): Int {
    val arr: IntArray = IntArray(108)
    var left = 0
    var right = 0
    var res = 0
    if (s.equals(""))return 0
    if (s.toCharArray()[0] == ' ') return 1
    for ((index,value) in s.toCharArray().withIndex()) {

    }

    s.toCharArray().forEachIndexed() { index, value ->
        right = index
        //如果当前字符在数组中已经存在，那么就将左指针移动到当前字符的下一个位置
        if (arr[value - '!'+1] == 0) {
            res = max(res, right - left)
            val nextIndex = arr[value - '!'+1] -1
            while (left <= nextIndex){
                arr[s[left] - '!'+1] = 0
                left++
            }
        }
        arr[value - '!'+1] = index+1
    }
    return max(res, right - left + 1)
}


fun findAnagrams(s: String, p: String): List<Int> {
    fun check(p:String):Boolean{
        val c = p[0]
        p.toCharArray().forEachIndexed{ index, char ->
            if (char != c) return false
        }
        return true
    }
    val res: MutableList<Int> = mutableListOf()
    fun helpCheck(p: String, arr: IntArray): Boolean{
         p.toCharArray().forEachIndexed{ index, char ->
             if ( !check(p) && arr[char-'a'] != 1 ) return false
             if (check(p) && arr[char-'a'] != p.length) return false
         }
        return true
    }


    var arr: IntArray = IntArray(25){0}
    var curlen: Int = 0
    s.toCharArray().forEachIndexed{ index, char ->

        //没有就让他直接滚并重置
        if (!p.contains(char)) {
            arr = IntArray(25){0}
            curlen = 0
            return@forEachIndexed
        }
        if (arr[char-'a'] == 0) {
            curlen++
            arr[char-'a'] = 1
        //如果出现了重复的不应该全部重置，只重置当前的值
        }else if (arr[char-'a'] == 1 && !check(p)){
            curlen = curlen
        }else{
            curlen++
            arr[char-'a']++
        }
        if (curlen.equals(p.length)) {
            when (helpCheck(p,arr)){
                //成功了话不应该全部重置，只重置当前的值
                true -> {
                    val firstIndex = index - p.length + 1
                    res.addLast(firstIndex)
                    arr[s[firstIndex] - 'a']--
                    curlen--
                    return@forEachIndexed
                }
                false -> {
                    arr = IntArray(25){0}
                    curlen = 0
                    return@forEachIndexed
                }
            }
        }
    }
    return res
}

















