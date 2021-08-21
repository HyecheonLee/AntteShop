package com.hyecheon.antteshop.web.dto

import com.hyecheon.antteshop.utils.saveFile
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
data class UserUpdateDto(
    var password: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var enabled: Boolean? = null,
    var image: MultipartFile? = null,
    @field:NotEmpty
    var roles: MutableSet<Long>? = HashSet(),
    var photos: String? = null,
) {
    fun saveImage(uploadDir: String) = run {
        image?.let { multipartFile ->
            multipartFile.originalFilename?.let {
                val fileName = StringUtils.cleanPath(it)
                photos = multipartFile.saveFile(uploadDir, fileName)
            }
        }
    }
}