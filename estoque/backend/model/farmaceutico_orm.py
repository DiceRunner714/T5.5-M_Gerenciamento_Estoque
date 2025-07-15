from sqlalchemy import Column, Integer, String, Boolean, ForeignKey, Float
from config.database import Base
from sqlalchemy.orm import relationship


class FarmaceuticoORM(Base):
    __tablename__ = "farmaceutico"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, nullable=False)
    categoria = Column(String, nullable=False)
    quantidade = Column(Integer, nullable=False)
    valor = Column(Float, nullable=False)
    restrito = Column(Boolean, nullable=False)
    tarja = Column(String, nullable=False)
    composicao = Column(String, nullable=False)
    retencao_de_receita = Column(Boolean, nullable=False)
    generico = Column(Boolean, nullable=False)
    receita = Column(Boolean, nullable=False)
    filial_id = Column(Integer, ForeignKey("filial.id", ondelete="CASCADE"))

    filial = relationship("FilialORM", back_populates="farmaceuticos")
