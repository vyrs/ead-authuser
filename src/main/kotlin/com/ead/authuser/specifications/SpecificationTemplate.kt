package com.ead.authuser.specifications

import com.ead.authuser.models.UserCourseModel
import com.ead.authuser.models.UserModel
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Join
import javax.persistence.criteria.Root


object SpecificationTemplate {
    @And(
        Spec(path = "userType", spec = Equal::class),
        Spec(path = "userStatus", spec = Equal::class),
        Spec(path = "email", spec = Like::class),
        Spec(path = "fullName", spec = Like::class)
    )
    interface UserSpec: Specification<UserModel>

    fun userCourseId(courseId: UUID): Specification<UserModel> {
        return Specification { root: Root<UserModel>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val userProd: Join<UserModel, UserCourseModel> = root.join("usersCourses")
            cb.equal(userProd.get<UUID>("courseId"), courseId)
        }
    }
}