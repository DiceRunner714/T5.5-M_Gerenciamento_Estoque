from pydantic import BaseModel


class FarmaceuticoCreate(BaseModel):
    nome: str
    crf: str
    filial_id: int


class FarmaceuticoResponse(FarmaceuticoCreate):
    id: int
    ativo: bool

    class Config:
        from_attributes = True
