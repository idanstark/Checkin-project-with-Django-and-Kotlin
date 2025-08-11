from django.db import models

class Cadastro(models.Model):
    id_usuario = models.AutoField(primary_key=True)
    nome = models.CharField(max_length=100)
    checkin = models.BooleanField(default=False, blank=True, null=True)
    latitude = models.FloatField(blank=True, null=True) 
    longitude = models.FloatField(blank=True, null=True) 

    def __str__(self):
        return self.nome
