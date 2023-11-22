function validarEmail(email){
	const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return regex.test(email);
}

function validarEdad(fecNacString){
	const fechaNac = new Date(fecNacString);
	const edad = new Date().getFullYear() - fechaNac.getFullYear();
	return edad > 18;
}