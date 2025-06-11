from model.empresa import Empresa

empresas: dict[int, Empresa] = {}
id_counter = 1

def criar_empresa(nome: str) -> Empresa:
    global id_counter
    nova = Empresa(nome)
    empresas[id_counter] = nova
    id_counter += 1
    return nova

def listar_empresas() -> list[tuple[int, Empresa]]:
    return list(empresas.items())

def buscar_empresa_por_id(id: int) -> Empresa | None:
    return empresas.get(id)

def remover_empresa(id: int) -> bool:
    if id in empresas:
        del empresas[id]
        return True
    return False

def get_empresas_dict():
    return empresas