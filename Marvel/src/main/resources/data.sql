INSERT INTO public.universo
(nombre)
VALUES('Marvel');

INSERT INTO public.poder
(nombre)
VALUES('Fuerza');

INSERT INTO public.super_heroe
(live, nombre, universo_id)
VALUES(false, 'Hulk', 1);

INSERT INTO public.universo
(nombre)
VALUES('DC');

INSERT INTO public.poder
(nombre)
VALUES('Volar');

INSERT INTO public.super_heroe
(live, nombre, universo_id)
VALUES(false, 'Superman', 1);

INSERT INTO public.super_heroe_poderes
(super_heroe_id, poderes_id)
VALUES(1,1);

INSERT INTO public.super_heroe_poderes
(super_heroe_id, poderes_id)
VALUES(2,1);

INSERT INTO public.super_heroe_poderes
(super_heroe_id, poderes_id)
VALUES(2,2);