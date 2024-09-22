package com.example.myapplication.models
data class UserData(
    val _id: String,
    val apellido: String,
    val apto_medico: AptoMedico,
    val deuda_arancel: Int,
    val documento: String,
    val email: String,
    val externo: Boolean,
    val fecha_vigencia: String,
    val habilitaciones_cambio: List<HabilitacionCambio>,
    val last_subscription_date: String,
    val nacimiento: String,
    val nombre: String,
    val plan_details: PlanDetails,
    val sede_socio: String?,
    val sede_socio_name: String,
    val seller_merchant_id: String,
    val status: String,
    val telefono: String,
    val terceros: String
)

data class AptoMedico(
    val fecha_vigencia: String,
    val url: String
)

data class HabilitacionCambio(
    val fecha: String,
    val modified_by: String
)

data class PlanDetails(
    val _id: String,
    val convenio: String,
    val merchant_id: String,
    val name: String,
    val nivel_de_acceso: String,
    val sede_local: String?,
    val sede_local_name: String,
    val vertical: String
)
