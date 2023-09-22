package io.john6.johnbase.compose.picker.dialog.multiple

import io.john6.johnbase.compose.picker.JWheelPickerInfo
import io.john6.johnbase.compose.picker.bean.JWheelPickerItemInfo

/**
 * Adapter for generate data for [io.john6.johnbase.compose.picker.JMultiWheelPicker]
 */
interface IMultipleJPickerAdapter {
    val wheelCount: Int
    val initialIndexes: Array<Int>
    fun key(wheelIndex: Int, selectedIndexes: List<Int>): Any
    fun generateJWheelPickerInfo(wheelIndex: Int, selectedIndexes: List<Int>): JWheelPickerInfo

    companion object {

        val testAdapter: IMultipleJPickerAdapter by lazy {
            object : IMultipleJPickerAdapter {
                override val wheelCount: Int
                    get() = 2
                override val initialIndexes: Array<Int>
                    get() = arrayOf(6, 6)

                override fun key(wheelIndex: Int, selectedIndexes: List<Int>): Any {
                    return if (wheelIndex == 0) {
                        0
                    } else {
                        selectedIndexes[0]
                    }
                }

                override fun generateJWheelPickerInfo(
                    wheelIndex: Int,
                    selectedIndexes: List<Int>
                ): JWheelPickerInfo {
                    return JWheelPickerInfo(
                        id = wheelIndex,
                        itemCount = when (wheelIndex) {
                            0 -> 10
                            else -> {
                                if(selectedIndexes[0] == 2) 0
                                else 20
                            }
                        },
                        initialIndex = initialIndexes[wheelIndex],
                        itemData = { index ->
                            if (wheelIndex == 0) {
                                JWheelPickerItemInfo(
                                    id = (index).toString(),
                                    index = index,
                                    fallbackText = 65.toChar().plus(index).toString()
                                )
                            } else {
                                val start = 10 * selectedIndexes[0]
                                JWheelPickerItemInfo(
                                    id = (start + index).toString(),
                                    index = index,
                                    fallbackText = (start + index).toString()
                                )
                            }
                        })
                }

            }

        }


        fun create(clazz: Class<out IMultipleJPickerAdapter>): IMultipleJPickerAdapter {
            return clazz.getDeclaredConstructor().newInstance()
        }
    }
}