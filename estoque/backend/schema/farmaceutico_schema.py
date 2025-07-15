from pydantic import BaseModel


class FarmaceuticoCreate(BaseModel):
    nome: str
    categoria: str
    quantidade: int
    valor: float
    tarja: str
    composicao: str
    retencao_de_receita: bool
    generico: bool
    receita: bool
    filial_id: int


class FarmaceuticoResponse(FarmaceuticoCreate):
    id: int
    nome: str
    categoria: str
    quantidade: int
    valor: float
    tarja: str
    composicao: str
    retencao_de_receita: bool
    generico: bool
    receita: bool
    filial_id: int

    class Config:
        from_attributes = True
